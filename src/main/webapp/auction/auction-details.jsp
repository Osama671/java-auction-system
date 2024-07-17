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
<h1>Auction Details</h1>

<p>Auction title: <%=auction.getTitle()%></p>
<p>Auction description: <%=auction.getDescription()%></p>

<% if (auction.getImageBase64() != null) { %>
<img src="data:image/jpeg;base64,<%=auction.getImageBase64()%>" alt="Auction Image" height="500px" width="500px"/>
<% } %>
</body>
</html>
