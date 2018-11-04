<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>账号设置</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/elusive-webfont.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
	<style type="text/css">
		.error{
			color:red;
		}
		input{
			margin-right:10px;
		}
		#tip-alert{
			display: none;
		}
		</style>
</head>
<body>
<%@ include file="include/top.jsp" %>

	<div class="container content">
		<a href="${pageContext.request.contextPath}/home?p=1" class="pull-right btn"><i class="icon-share-alt"></i> Go back</a>
	</div>

	<div class="container content main">
		<form class="form-horizontal fm" id="fm">

			<div id="tip-alert" class="alert"></div>

			<legend>账号设置</legend>
			<input id="userid" type="hidden" name="id" value="${user.id }"/>
			<div class="control-group">
			    <label class="control-label">昵称</label>
			    <div class="controls">
			      <input type="text" class="span4" value="${user.nickname }" disabled="disabled">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" >真实姓名</label>
			    <div class="controls">
			      <input type="text" class="span4" value = "${user.realname }" disabled="disabled">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="inputEmail">电子邮件</label>
			    <div class="controls">
			      <input type="text" id = "inputEmail" class="span4" name="email" value="${user.email}">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="qianming">个人签名</label>
			    <div class="controls">
			      <input type="text" class="span4" id="qianming" name="qianming" value="${user.qianming }">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="qq">QQ</label>
			    <div class="controls">
			      <input type="text" class="span4" id="qq" name="qq" value="${user.qq}">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="twitter">Twitter</label>
			    <div class="controls">
			      <input type="text" class="span4" id="twitter" name="twitter" value="${user.twitter }">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="github">Github</label>
			    <div class="controls">
			      <input type="text" class="span4" id="github" name="github" value="${user.github }">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="weibo">新浪微博</label>
			    <div class="controls">
			      <input type="text" class="span4" id="weibo" name="weibo" value="${user.weibo}">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="blog">个人主页</label>
			    <div class="controls">
			      <input type="text" class="span4" id="blog" name="blog" value="${user.blog }">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="location">所在地</label>
			    <div class="controls">
			      <input type="text" class="span4" id="location" name="location" value="${user.location }">
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<button type="button" id="fmbtn" class="btn">保存设置</button>
				</div>
			</div>
			<legend>头像设置</legend>
			<img src="http://www.gravatar.com/avatar/${user.pic }?s=80&d=mm&r=g" alt="" class="img-circle">
			头像服务采用<a href="http://en.gravatar.com/">Gravatar</a>服务，请到该站点进行设置
			<<br/>
			<form  id="uploadForm" class="form-horizontal"  action="${pageContext.request.contextPath}/uploadavatar" method="post"  enctype="multipart/form-data">
				<input id="fileUpLoad" type="file" name="file">
				<button type="button" id="uploadBtn" class="btn">上传</button>
			</form>

		</form>

		<form class="form-horizontal fm"id="fm2">
			<legend>密码设置</legend>
			<div class="control-group">
			    <label class="control-label" for="oldpassword">原密码</label>
			    <div class="controls">
			      <input type="password" id="oldpassword"name = "oldpassword"class="span4">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="password">新密码</label>
			    <div class="controls">
			      <input type="password" class="span4" id="password" name="password">
				</div>
			</div>
			<div class="control-group">
			    <label class="control-label" for="repassword">确认密码</label>
			    <div class="controls">
			      <input type="password" class="span4"id="repassword" name="repassword">
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<button type="button" id="fm2btn" class="btn">保存密码</button>
				</div>
			</div>
		</form>
	</div>

	
	<%@ include  file="include/footer.jsp"%>
	<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/validate/jquery.validate.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/common.js"></script>
	<script>
		$(function(){

            $("#uploadBtn").click(function(){
                console.log("---#####----")
                // $("#uploadForm").submit();

                var file = checkFile();
                if (!file) {
                    alert('请先选择文件');
                    return false;
                };

                // 构建form数据
                var formFile = new FormData();
                formFile.append("action", "uploadavatar");
                //把文件放入form对象中
                formFile.append("file", file);


                $.ajax({
                    url:getRootPath()+"/uploadavatar",
                    type:"post",
                    dataType:"json",
                    cache: false,//上传文件无需缓存
                    processData: false,//用于对data参数进行序列化处理 这里必须false
                    contentType: false, //必须
					data:formFile,
                    success:function(data){
                        console.log("data:  "+JSON.stringify(data));

                    }
				});
			});

			$("#fmbtn").click(function(){
			    // $("#fm").submit();
				// TODO ajax 请求
				$.ajax({
					url:getRootPath()+"/account-setting",
					type:"post",
					dataType:"json",
					data:{
					//	userid inputEmail  qianming  qq  twitter  github  weibo  blog location
                        userid:$("#userid").val(),
						email:$("#inputEmail").val(),
                        qianming:$("#qianming").val(),
                        qq:$("#qq").val(),
                        twitter:$("#twitter").val(),
                        github:$("#github").val(),
                        weibo:$("#weibo").val(),
                        blog:$("#blog").val(),
                        location:$("#location").val()
					},
					success:function(data){
					    console.log("data:  "+JSON.stringify(data));
                        tip(data.msg,data.code);
						if(data.code == 200){
                            $("#inputEmail").val(data.data.email);
                            $("#qianming").val(data.data.qianming);
                            $("#qq").val(data.data.qq);
                            $("#twitter").val(data.data.twitter);
                            $("#github").val(data.data.github);
                            $("#weibo").val(data.data.weibo);
                            $("#blog").val(data.data.blog);
                            $("#location").val(data.data.location);
						}
					}
				});
            });
			$("#fm2btn").click(function(){
			    // $("#fm2").submit();
               if($("#oldpassword").val() == ""){
                    tip("请输入原始密码",0);
                    return;
                }
                if($("#password").val() == ""){
                    tip("请输入新密码",0);
                    return;
                }
                if($("#repassword").val() == ""){
                    tip("请输入确认密码",0);
                    return;
                }

				// TODO ajax 请求
				$.ajax({
					url:getRootPath()+"/resetpassword",
					type:"post",
					dataType:"json",
					data:{
						// oldpassword  password repassword
                        oldpassword:$("#oldpassword").val(),
                        password:$("#password").val()
					},
					success:function(data){
                        tip(data.msg,data.code);
					}
				})
			});

    		$("#fm").validate({
    			rules:{
    				email:{
    					required:true,
    					email:true
    				},
    				qq:{
    					number:true
    				},
    				blog:{
    					url:true
    				}
    			},
    			messages:{
    				email:{
    					required:'请输入电子邮件',
    					email:'电子邮件格式错误'
    				},
    				qq:{
    					number:"请输入正确的QQ号码"
    				},
    				blog:{
    					url:"请输入正确的URL地址"
    				}
    				
    			}
    		});
    		$("#fm2").validate({
    			rules:{
    				oldpassword:{
    					required:true,
    					rangelength:[6,18]
    				},
    				password:{
    					required:true,
    					rangelength:[6,18],
    					equalTo:'#password'
    				},
    				repassword:{
    					required:true,
    					rangelength:[6,18],
    					equalTo:'#password'
    				}
    			},
    			messages:{
    				oldpassword:{
    					required:'请输入旧密码',
    					rangelength:'密码长度为6~18位'
    				},
    				password:{
    					required:'请输入新密码',
    					rangelength:'密码长度为6~18位'
    				},
    				repassword:{
    					required:'请输入确认密码',
    					rangelength:'密码长度为6~18位',
    					equalTo:'两次密码不一致'
    				}
    			}
    		});

            function tip(text,code) {
                if(code == 200){
                    $("#tip-alert").addClass("alert-success").show();
				}else{
                    $("#tip-alert").addClass("alert-error").show();
				}
                $("#tip-alert").html(text);
                setTimeout(function () {
                    $("#tip-alert").hide();
                }, 3000);
            }

            // 检测是否选择文件，如果选择，返回文件对象;如果没选择，返回false
            function checkFile(){
                // 获取文件对象(该对象的类型是[object FileList]，其下有个length属性)
                var fileList = $('#fileUpLoad')[0].files;

                // 如果文件对象的length属性为0，就是没文件
                if (fileList.length === 0) {
                    console.log('没选择文件');
                    return false;
                };
                return fileList[0];
            };


		});
	</script>
</body>
</html>
