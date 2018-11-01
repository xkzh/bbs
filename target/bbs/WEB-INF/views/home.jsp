<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/elusive-webfont.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
</head>
<body>
<%@ include file="include/top.jsp" %>
################ @@ ${pageContext.request.contextPath} @@
<div class="container content toolbar">
    <a href="#" class="btn">全部</a>&nbsp;&nbsp;&nbsp;&nbsp;
    <c:forEach items="${typeList }" var = "tl">
        <a href="${pageContext.request.contextPath}/posttype?t=${tl.alias}" class="nolink">${tl.name}</a>&nbsp;&nbsp;&nbsp;&nbsp;
    </c:forEach>
</div>

<div class="container content">
    <ul class="unstyled talks">
        <c:if test="${empty pageBean.recordList}">
            <li>
                <h4>暂时还没有人发布内容！</h4>
            </li>
        </c:if>
        <c:forEach items="${pageBean.recordList}" var="pl">
            <li>
                <h4>
                    <c:if test="${pl.toppost}">
                        <i class="icon-bookmark-empty" title="置顶"></i>
                    </c:if>
                    <a href="post.do?id=${pl.id}">${pl.title}</a>
                </h4>
              <small>
                    <a href="#">discuss(${pl.commentsnum})</a> / <a href="/account-info.do?u=${pl.user.nickname}">@${pl.user.nickname}</a>
                        ${pl.likenum} <i class="icon-heart-empty" style="color:red;"></i>
                    &nbsp;&nbsp;${pl.lastcommentstime}
                    <span class="label label-inverse" style="background-color:${pl.postType.color}"><a href="${pageContext.request.contextPath}/go.do?t=${pl.postType.alias}">${pl.postType.name}</a></span>
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
                        <li><a href="${pageContext.request.contextPath}/home.do?p=${pageBean.currentPage-1}">Prev</a></li>
                    </c:otherwise>
                </c:choose>
                <c:forEach items="${listNum}" var="p">
                    <li><a href="${pageContext.request.contextPath}/home.do?p=${p }">${p}</a></li>
                </c:forEach>
                <c:choose>
                <c:when test="${pageBean.currentPage==pageBean.pageCount}">
                <li class="disabled"><a href="javascript:;">Next</a>
                    </c:when>
                    <c:otherwise>
                <li><a href="${pageContext.request.contextPath}/home.do?p=${pageBean.currentPage+1}">Next</a>
                    </c:otherwise>
                    </c:choose>
            </ul>
        </div>
    </c:if>
</div>


<%@ include file="include/footer.jsp" %>
<script src="${pageContext.request.contextPath}/static/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
</body>
</html>