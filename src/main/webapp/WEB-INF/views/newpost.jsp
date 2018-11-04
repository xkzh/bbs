<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>发帖</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/elusive-webfont.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
	<style type="text/css">
		#result_tip{
			display: none;
		}
	</style>
</head>
<body>
	<%@ include file="include/top.jsp"%>

	<div class="container content">
		<a href="${pageContext.request.contextPath}/home?p=1" class="pull-right btn"><i class="icon-share-alt"></i>
			Go back</a>
	</div>

	<div class="container content main">
		<form>
			<div id="result_tip" class="alert alert-error" class="span10"></div>
			<fieldset>
				<legend>发布新帖子:</legend>
				<input id="hidden_alias" type="hidden" name="alias" value="${pt.alias }">
				<input id="hidden_typeid" type="hidden" name="typeId" value="${pt.id }">
				<div class="controls controls-row">
					<input class="span5" type="text" id="title" name="title"
						placeholder="Title">
				</div>
				<input type="text" class="span5" id="url" name="url" placeholder="URL">
				<br />
				<input type="hidden" name="content" id="content">
				<textarea class="span10" rows="10" id="md" style="resize:none;"placeholder="Content"></textarea>
			 	<br />
				<button type="button" id="sender" class="btn sender">发布</button>
				<span class="help-inline">支持<a href="http://markdown.tw/"
					target="_blank">MarkDown</a>语法</span>
			</fieldset>
		</form>
	</div>


	<%@ include file="include/footer.jsp"%>
	<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/validate/jquery.validate.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/jquery.autosize-min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/markdown.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/common.js"></script>
	<script>
		$(function() {
			$(".fm").validate({
				rules : {
					title : {
						required : true
					},
					url : {
						url : true
					}
				},
				messages : {
					title : "请输入标题内容",
					url : "请输入正确的url地址"
				}
			});
			$('#md').autosize();
			$("#sender").click(function() {
				var src = $("#md").val();
				var md = markdown.toHTML(src);
				$("#content").val(md);
				// $(".fm").submit();

				if($("#title").val() == ""){
                    tip("请输入标题");
                    return;
				}
                if($("#content").val() == ""){
                    tip("请输入内容");
                    return;
                }

				// 执行ajax方法
				$.ajax({
					url:getRootPath()+"/newpost",
					type:"post",
					dataType:"json",
					data:{
						alias:$("#hidden_alias").val(), // hidden_alias  hidden_typeid
						typeId:$("#hidden_typeid").val(),
						title:$("#title").val(),
						url:$("#url").val(),
						content:$("#content").val(),
					},
					success:function(data){
						console.log(""+JSON.stringify(data));
						if(data.code == 200){
							location.href = getRootPath()+"/posttype?t=${pt.alias}&p=1"
						}else{
                            tip(data.msg);
						}
					}
				})
			});
			//在textarea中支持table键
			$(document)
					.delegate(
							'textarea',
							'keydown',
							function(e) {
								var keyCode = e.keyCode || e.which;

								if (keyCode == 9) {
									e.preventDefault();
									var start = $(this).get(0).selectionStart;
									var end = $(this).get(0).selectionEnd;
									$(this).val(
											$(this).val().substring(0, start)
													+ "\t"
													+ $(this).val().substring(
															end));
									$(this).get(0).selectionStart = $(this)
											.get(0).selectionEnd = start + 1;
								}
							});
            function tip(text) {
                $("#result_tip").show();
                $("#result_tip").html(text);
                setTimeout(function () {
                    $("#result_tip").hide();
                }, 3000);
            }
		});
	</script>
</body>
</html>
