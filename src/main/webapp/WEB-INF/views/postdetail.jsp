<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>BBS-Rework-${post.title}</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/elusive-webfont.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<%@ include file="include/top.jsp"%>
	<div class="container content">
		<a href="${pageContext.request.contextPath}/home?p=1" class="pull-right btn"><i class="icon-share-alt"></i> Go back</a>
	</div>

<div class="container content main">
		<div class="post">
			<h3 class="post-title">${post.title}</h3>
			<div class="post-content">
				${post.content}
			</div>
			<c:if test="${not empty post.url}">
				<p class="ref">
					<a target="_blank" href="${post.url }"><i class="icon-paper-clip"></i> ${post.url}</a>
				</p>
			</c:if>
			<div class="author">
				<img class="img-circle" src="http://www.gravatar.com/avatar/${post.user.pic}?s=30&d=mm&r=g" alt="">
				created by <a href="/account-info.do?u=${post.user.nickname}">${post.user.nickname}</a>
				  @ ${fn:substring(post.createtime,0,19)}
				  &nbsp;&nbsp;${post.viewnum}次浏览&nbsp;&nbsp;
				<small class="pull-right">
					<i class="icon-heart text-error"></i>[${post.likenum}]<a href="like.do?id=${post.id}">喜欢</a>&nbsp;
					<i class="icon-star text-warning"></i><a href="">收藏</a>&nbsp;
					<c:if test="${sessionScope.curr_user.id == post.user.id }">
						<i class="icon-edit text-info"></i><a href="">编辑</a>&nbsp;
						<i class="icon-trash text-info"></i><a href="delpost.do?id=${post.id }">删除</a>
					</c:if>
				</small>
			</div>
			<div class="response-fm">
				
				<form id="fm" action="comment.do" method="post">
					<fieldset>
						<h4>我要回复</h4>
						<input type="hidden" name="postid" value="${post.id}"/>
						<input type="hidden" name="content" id = "content"/>
						<textarea id="md" class="span10" style="resize:none;" rows="5"></textarea><br/>
						<button type="button" class="btn sender">回复</button> 
						<span class="help-inline">支持<a href="http://markdown.tw/" target="_blank">MarkDown</a>语法</span>
					</fieldset>
				</form>
			</div>
		</div>
		<c:choose>
		<c:when test="${empty commentList}">
			<div class="comments">
				<div class="comment">
					<p>该贴还没有人回复！</p>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="comments">
				<c:forEach items="${commentList}" var="c">
						<c:if test="${c.enable || sessionScope.curr_user.id == c.userId}">							
						<div class="comment">
							<div class="cm-author">
								<img class="img-circle" src="http://www.gravatar.com/avatar/${c.user.pic }?s=30&d=mm&r=g" alt="">
								&nbsp;&nbsp;
								<a href="/account-info.do?u=${c.user.nickname}">@${c.user.nickname}</a>
								&nbsp;&nbsp;
								# ${fn:substring(c.createtime,0,19)}
								&nbsp;&nbsp;
								<a href="javascript:;" class="hide replay" ref="${c.user.nickname}"><i class="icon-share-alt"></i> 回复</a>
								&nbsp;&nbsp;
								
								<c:if test="${sessionScope.curr_user.role =='admin'}">
								<a href="commentEnable.do?pid=${c.post_id}&cid=${c.id}" class="hide del" ref="101"><i class="icon-eye-close"></i> 屏蔽此内容</a>
								</c:if>
							</div>
							<div class="cm-content">
								<p>${c.content }</p>
							</div>
						</div>
						</c:if>
					</c:forEach>
				</div>
		</c:otherwise>
		</c:choose>
		<a name="replay"></a>
	</div>

	
	<%@ include file="include/footer.jsp" %>
	<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/jquery.autosize-min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/markdown.js"></script>
	<script>
		$(function(){
			$('#content').autosize(); 
			$(".sender").click(function(){
				var src = $("#md").val();
				if(src.trim()==""){
					$("#md").val("");
					$("#md").focus();
				}else{
					var md = markdown.toHTML(src);
					$("#content").val(md);
					$("#fm").submit();
				}
			});

			/*回复超链接的显示*/
			$(".cm-author").mouseover(function(){
				$(this).children(".replay,.del").show();
			}).mouseout(function(){
				$(this).children(".replay,.del").hide();
			});
			/*回复的事件*/
			$(".replay").click(function(){
				$("#md").focus();
				$("#md").val("@"+$(this).attr("ref")+" ");
			});
			
			$(document).delegate('textarea', 'keydown', function(e) {
			  var keyCode = e.keyCode || e.which;
			
			  if (keyCode == 9) {
				e.preventDefault();
				var start = $(this).get(0).selectionStart;
				var end = $(this).get(0).selectionEnd;
				$(this).val($(this).val().substring(0, start)
							+ "\t"
							+ $(this).val().substring(end));
				$(this).get(0).selectionStart =
				$(this).get(0).selectionEnd = start + 1;
			  }
			});
		});
	</script>
</body>
</html>