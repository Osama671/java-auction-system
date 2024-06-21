<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Auction" %><%--
  Created by IntelliJ IDEA.
  User: george
  Date: 6/21/24
  Time: 2:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auction</title>
    <%@include file="/common.jsp" %>
</head>
<body>
<%
    Auction auction = (Auction) request.getAttribute("auction");
%>
Auction Details go here

Auction id: <%=auction.getId()%>
</body>
</html>
