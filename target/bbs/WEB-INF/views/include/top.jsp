<%@page import="com.xk.bbs.bean.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% User user = (User)request.getSession().getAttribute("curr_user");
	int notesNum = 0;//new UserService().countAction();
	int n =  0;// new NotifyService().countByRead(user.getId());
	String url = request.getRequestURI();
//	boolean nowpage = url.endsWith("/admin.jsp") || url.endsWith("/newNotes.jsp");
%>
<div class="navbar navbar-static-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="brand" href="home.do">BBS-ReWork</a>
			<ul class="nav pull-right">
				<li><a href="home.do"><i class="icon-home"></i> 首页</a></li>
				<c:if test="${sessionScope.curr_user.role =='admin'}">
					<li><a href="admin.do"><i class="icon-cog"></i> 用户管理
						<%--<c:if test="<%=notesNum != 0 && !nowpage%>">
							<span class="badge badge-important"><%=notesNum %></span>
						</c:if>--%>
					</a></li>
				</c:if>
				<li class="dropdown">
					<a href="javascript:;" data-toggle="dropdown" class="dropdown-toggle" role="button" id="drop2">
						<i class="icon-user"></i>
						${curr_user.nickname }
						<b class="caret"></b>
						<c:if test="<%=n != 0 %>">
							<span class="badge badge-important"><%=n %></span>
						</c:if>
					</a>
					<ul aria-labelledby="drop2" role="menu" class="dropdown-menu">
						<li role="presentation"><a href="account-info.do?u=${curr_user.nickname }" tabindex="-1" role="menuitem">个人主页</a></li>
						<li role="presentation"><a href="setting.do" tabindex="-1" role="menuitem">账号设置</a></li>
						<li role="presentation"><a href="notify.do" tabindex="-1" role="menuitem">通知中心
							<c:if test="<%=n != 0 %>">
								<span class="badge badge-important"><%=n %></span>
							</c:if>
						</a></li>
						<li class="divider" role="presentation"></li>
						<li role="presentation"><a href="logout.do" tabindex="-1" role="menuitem">安全退出</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</div>
