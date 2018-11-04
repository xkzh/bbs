<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>个人主页</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/elusive-webfont.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/weather.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/fontello.css">
		
	</head>
	<body>
		<%@ include file="include/top.jsp"%>
		<div class="container content main">
			<div class="account-bg">
				<img
					src="http://www.gravatar.com/avatar/${user.pic }?s=100&d=mm&r=g"
					alt="" class="img-circle">
				<h3>${user.nickname }</h3>
				<p>${user.qianming }<br /><br />
					<a target="_blank" class="blog_link" href="${user.blog }">${user.blog }</a>
				</p>
			</div>
		</div>

		<div class="container content main">
			<ul class="unstyled account-label">
				<li><h4><i class="icon-social-db-shape"></i> ${user.realname }</h4></li>
				<c:if test="${not empty user.location }">
					<li><h4><i class="icon-map-marker"></i><a target="_blank" href="https://ditu.google.com/maps?q=${user.location }">${user.location }</a></h4></li>
				</c:if>
				<c:if test="${not empty user.twitter}">
					<li><h4><i class="icon-twitter"></i><a target="_blank" href="https://twitter.com/${user.twitter }">${user.twitter }</a></h4></li>
				</c:if>
				<c:if test="${not empty user.github}">
					<li><h4><i class="icon-github"></i><a target="_blank" href="https://github.com/${user.github }">${user.github }</a></h4></li>
				</c:if>
				<c:if test="${not empty user.weibo}">
					<li><h4><i class="icon-social-sina-weibo"></i><a target="_blank" href="http://www.weibo.com/${user.weibo }">${user.weibo }</a></h4></li>
				</c:if>
				<c:if test="${not empty user.qq}">
					<li><h4><i class="icon-social-qq"></i><a target="_blank" href="http://user.qzone.qq.com/${user.qq }">${user.qq }</a></h4></li>
				</c:if>
				<c:if test="${not empty user.location}">
					<li><h4><i class="icon-weather-sun"></i></h4></li>
				</c:if>
				<div class="clearfix"></div>
			</ul>
		</div>

		<div class="container content">
			<ul class="unstyled talks">
				<c:if test="${empty pageBean.recordList}">
					<h4 class="account-talks-title">
						${user.nickname }分享的内容
					</h4>
					<li>
						<h4>暂时还没有发布任何内容！</h4>
					</li>
				</c:if>
				<c:forEach items="${pageBean.recordList}" var="pl">
				<li>
					<h4>
						<c:if test="${pl.toppost}">
							<i class="icon-bookmark-empty" title="置顶"></i>
						</c:if>
						 <a href="${pageContext.request.contextPath}/postdetail?id=${pl.id}">${pl.title}</a>
					</h4>
					<small>
						<a href="#">discuss(${pl.commentsnum})</a> / <a href="${pageContext.request.contextPath}/account-info?p=1&u=${pl.user.nickname}">@${pl.user.nickname}</a>
						 ${pl.likenum} <i class="icon-heart-empty" style="color:red;"></i> 
						 &nbsp;&nbsp;${pl.lastcommentstime}  
						 <span class="label label-inverse" style="background-color:${pl.postType.color}"><a href="${pageContext.request.contextPath}/posttype?p=1&t=${pl.postType.alias}">${pl.postType.name}</a></span>
					</small>
				</li>
			</c:forEach>
			</ul>
		</div>
		<div class="container">
		<c:if test="${pageBean.recordCount > 10}">
			<div class="pagination pagination-right">
				  <ul>
				  <c:choose>
				  	<c:when test="${pageBean.currentPage == 1 }">
					    <li class="disabled"><a href="javascript:;" >Prev</a></li>
				  	</c:when>
				  	<c:otherwise>
					    <li><a href="${pageContext.request.contextPath}/account-info?p=1&u=${user.nickname}&p=${pageBean.currentPage-1}">Prev</a></li>
				  	</c:otherwise>
				  </c:choose>
				    <c:forEach items="${listNum}" var="p">
					    <li><a href="${pageContext.request.contextPath}/account-info?p=1&u=${user.nickname}&p=${p}">${p}</a></li>
				    </c:forEach>
				    <c:choose>
						<c:when test="${pageBean.currentPage==pageBean.pageCount}">
							<li class="disabled"><a href="javascript:;">Next</a>
						</c:when>
						<c:otherwise>
							<li><a href="${pageContext.request.contextPath}/account-info?p=1&u=${user.nickname }&p=${pageBean.currentPage+1}">Next</a>
						</c:otherwise>
					</c:choose>
				  </ul>
			</div>
		</c:if>
	</div>
		<%@ include file="include/footer.jsp"%>
		<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
		<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/static/js//jquery.autosize-min.js"></script>
		<script src="${pageContext.request.contextPath}/static/js/markdown.js"></script>
		<script>
	$(function() {
		$('#content').autosize();
		$(".sender").click(function() {
			var src = $("#content").val();
			var md = markdown.toHTML(src);
			$(".preview").html(md);
		});
	});
</script>
	</body>
</html>
