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
    // Get the auction object from request attribute
    Auction auction = (Auction) request.getAttribute("auction");

    // Ensure auction object is not null
    if (auction != null) {
%>
<div class="container">
    <h2>Auction Details</h2>
    <div>
        <h3>Title: <%= auction.getTitle() %></h3>
        <h5>Description: <%= auction.getDescription() %></h5>

    </div>
</div>
<% } else { %>
<div class="container">
    <p>Auction details not found.</p>
</div>
<% } %>
Auction id: <%=auction.getId()%>
</body>
</html>
