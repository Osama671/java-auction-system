<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <%@include file="common.jsp" %>
</head>
<body>
<%@include file="components/navbar.jsp" %>
<a class="btn btn-outline-dark" href="<%=request.getContextPath()%>/auction/create">Create Auction</a>
<h1><%= "Hello World!" %>
</h1>
<br/>
</body>
</html>