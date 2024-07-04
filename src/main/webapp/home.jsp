<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Auction" %>
<%@ page import="java.util.ArrayList" %>
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
<%
    ArrayList<Auction> auctions = (ArrayList<Auction>) request.getAttribute("auctions");
%>
<h1>Auctions</h1>
<br/>
<div class="container">
    <% if (auctions.isEmpty()) {%>
       <h3>No Auctions found</h3>
    <%}%>
    <div class="row">
        <% for (Auction auction : auctions) {%>
        <div class="col-md-12">
            <%= auction.getTitle() %>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>