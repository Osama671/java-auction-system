<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Auction" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.helper.AuctionHelper" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <%@include file="common.jsp" %>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/countdown.js" defer></script>
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
            <a href="<%=request.getContextPath() + "/auction/details?id=" + auction.getId()%>"><%= auction.getTitle() %>
            </a>
            <p><%= auction.getEndsAt() %>
            </p>
            <% if (auction.getState() == Auction.State.Open) {%>
            <p class="countdown"
               data-end-time="<%= auction.getEndsAt() %>"
               auction-state="<%= auction.getState()%>">
            </p><%
        } else {
        %>
            <p><%= AuctionHelper.getAuctionStateText(auction.getState()) %>
            </p> <%}%></div>
        <% } %>
    </div>
</div>

</body>
</html>

