
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div class="navbar navbar-static-top">
		<div class="navbar-inner">
	    	<div class="container">
	    		<a class="brand" href="home.do">BBS</a>
			    <ul class="nav pull-right">
			       <li><a href="home.do"><i class="icon-home"></i> 首页</a></li>
			       <c:if test="${sessionScope.curr_user.role =='admin'}">
			       <li><a href="admin.do"><i class="icon-cog"></i> 用户管理
							<span class="badge badge-important"></span>
			       </a>
				   </li>
					</c:if>
			      <li class="dropdown">
			     	 <a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle" role="button" id="drop2">
			     	 	<i class="icon-user"></i> 
			     	 	${curr_user.nickname }
						<b class="caret"></b>
						<span class="badge badge-important"></span>
					</a>
                      <ul aria-labelledby="drop2" role="menu" class="dropdown-menu">
                        <li role="presentation"><a href="account-info.do?u=${curr_user.nickname }" tabindex="-1" role="menuitem">个人主页</a></li>
                        <li role="presentation"><a href="setting.do" tabindex="-1" role="menuitem">账号设置</a></li>
                        <li role="presentation"><a href="notify.do" tabindex="-1" role="menuitem">通知中心
							<span class="badge badge-important"></span>
						 </a></li>
                        <li class="divider" role="presentation"></li>
                        <li role="presentation"><a href="logout.do" tabindex="-1" role="menuitem">安全退出</a></li>
                      </ul>
                    </li>
			    </ul>
	    	</div>
		</div>
	</div>
