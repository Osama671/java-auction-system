<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Auction" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Bid" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.helper.AuctionHelper" %><%--
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
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/countdown.js" defer></script>
</head>
<body>
<%@include file="../components/navbar.jsp" %>
<%
    // Get the auction object from request attribute
    Auction auction = (Auction) request.getAttribute("auction");
    Bid highestBid = (Bid) request.getAttribute("highestBid");
    int userId = (int) request.getAttribute("userId");
%>

<div class="container">
    <h2>Auction Details</h2>
    <div>
        <h3>Title: <%= auction.getTitle() %></h3>
        <h5>Description: <%= auction.getDescription() %></h5>

    </div>
</div>

Auction id: <%=auction.getId()%><br/>
Title: <%=auction.getTitle()%><br/>
Min Bid: <%=auction.getMinBid() / 100F%><br/>
Current Max Bix: <%= highestBid == null ? "No bids" : highestBid.getAmount() / 100F%><br/>
Status: <%=auction.getState()%><br/>
Closes At: <%=auction.getEndsAt()%><br/>
<% if (auction.getImageBase64() != null) { %>
<img src="data:image/jpeg;base64,<%=auction.getImageBase64()%>" alt="Auction Image" height="500px" width="500px"/>
<% } %>

<% if (auction.getState() == Auction.State.Open) {%><p class="countdown" data-end-time="<%= auction.getEndsAt() %>"
                                                       auction-state="<%= auction.getState()%>"></p><%}%>

<% if (auction.getCreatedBy().getId() == userId) {%>
<h3>You can't bid on your listing</h3>
<%} else if (auction.getState() == Auction.State.Open) { %>
<form method="post">
    <label for="bid">Bid: </label>
    <input type="text" id="bid" name="bid" required><br><br>
    <input type="hidden" id="auctionId" name="auctionId" value="<%=auction.getId()%>">
    <%
        var errors = (String[]) request.getAttribute("errors");
        if (errors != null && errors.length > 0) {
    %>
    <div class="text-danger">
        <ul>
            <% for (String error : errors) { %>
            <li><%= error %>
            </li>
            <% } %>
        </ul>
    </div>
    <% } %>
    <input type="submit" value="Submit Bid">
</form>
<%
} else {
%>
<h3><%= AuctionHelper.getAuctionStateText(auction.getState())%>
</h3>
<%
    }
%>

</body>
</html>
