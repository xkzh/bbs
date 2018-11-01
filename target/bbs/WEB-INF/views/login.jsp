<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Rework BBS</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/elusive-webfont.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
		<style type="text/css">
body {
	padding-top: 70px;
	background-image: url(${pageContext.request.contextPath}/static/img/bg.png);
	padding-bottom: 40px;
}

.form-signin {
	max-width: 430px;
	margin: 0px auto;
}

.form-signin .fm input[type='text'],.form-signin .fm input[type='password']
	{
	width: 365px;
}
#tip-login-first{
	display: none;
}
</style>
	</head>
	<body>

		<div class="container">

			<div class="widget form-signin">
				<h3>
					<i class="icon-lock-alt"></i> Rework BBS - 系统登录
				</h3>
				<form action="${pageContext.request.contextPath}/login" method="post" class="fm">

					<div id="tip-login-first" class="alert alert-error">

					</div>

					<label>
						电子邮件
					</label>
					<input type="text" id="email" name="email">
					<br>
					<label>
						密码
					</label>
					<input type="password" id="password" name="password">
					<br>
					<button type="button" class="login btn pull-right">
						进入系统
					</button>
					<a href="/reg.do">注册账号</a>
					<a href="repassword.do">忘记密码</a>
					<div class="clearfix"></div>
				</form>
			</div>
		</div>
		<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
		<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
		<script src="${pageContext.request.contextPath}/static/js/validate/jquery.validate.js"></script>
		<script src="${pageContext.request.contextPath}/static/js/sha.js"></script>
		<script src="${pageContext.request.contextPath}/static/js/common.js"></script>
		<script>
	$(function() {
		$(".login").click(function() {
			if($("#email").val() == "" && $("#password").val() == ""){
                tip("请先登录")
                return;
			}
            console.log("password val:   "+$("#password").val());
            var password = CryptoJS.SHA1($("#password").val());
            $("#password").val(password)
            console.log("password:   "+password);
			$.ajax({
				url:getRootPath()+"/login",
				type:"post",
				dataType:"json",
				data:{
					email:$("#email").val(),
					password:$("#password").val()
				},
				success:function(data){
                    if(data.code == 200){
						// 登录成功
                        location.href = getRootPath() + "/home?p=1";
					}else if(data.code == 1001){
                        tip(data.msg)
                        password:$("#password").val("")
					}else if(data.code == 1002){
                        tip(data.msg)
                    }else if(data.code == 1003){
                        tip(data.msg)
                    }else if(data.code == 500){
                        tip(data.msg)
                    }
				}
			});
		});
		$(".fm").validate({
			rules : {
				email : {
					required : true,
					email : true
				},
				password : {
					required : true
				}
			},
			messages : {
				email : {
					required : '请输入电子邮件地址',
					email : '电子邮件格式错误'
				},
				password : {
					required : "请输入密码"
				}

			}
		});
		$(":input").keydown(function(){
				$(".close").click();
			});

		function tip(text) {
            $("#tip-login-first").show();
            $("#tip-login-first").html(text);
            setTimeout(function () {
                $("#tip-login-first").hide();
            }, 3000);
        }
	});
</script>
	</body>
</html>