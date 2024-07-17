<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Auction" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Bid" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.helper.AuctionHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auction Details</title>
    <%@include file="/common.jsp" %>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/countdown.js" defer></script>
</head>
<body>
<%@include file="../components/navbar.jsp" %>
<%
    Auction auction = (Auction) request.getAttribute("auction");
    Bid highestBid = (Bid) request.getAttribute("highestBid");
    int userId = (int) request.getAttribute("userId");
%>

<h1>Auction Details</h1>

<p>Auction ID: <%=auction.getId()%></p>
<p>Title: <%=auction.getTitle()%></p>
<p>Min Bid: <%=auction.getMinBid() / 100F%></p>
<p>Current Max Bid: <%= highestBid == null ? "No bids" : highestBid.getAmount() / 100F%></p>
<p>Status: <%=auction.getState()%></p>
<p>Closes At: <%=auction.getEndsAt()%></p>

<% if (auction.getState() == Auction.State.Open) { %>
<p class="countdown" data-end-time="<%= auction.getEndsAt() %>" auction-state="<%= auction.getState()%>"></p>
<% } %>

<% if (auction.getCreatedBy().getId() == userId) { %>
<h3>You can't bid on your listing</h3>
<% } else if (auction.getState() == Auction.State.Open) { %>
<form method="post" action="<%= request.getContextPath() %>/auction/details">
    <label for="bid">Bid:</label>
    <input type="text" id="bid" name="bid" required><br><br>
    <input type="hidden" id="auctionId" name="auctionId" value="<%= auction.getId() %>">

    <% var errors = (String[]) request.getAttribute("errors");
        if (errors != null && errors.length > 0) { %>
    <div class="text-danger">
        <ul>
            <% for (String error : errors) { %>
            <li><%= error %></li>
            <% } %>
        </ul>
    </div>
    <% } %>

    <input type="submit" value="Submit Bid">
</form>
<% } else { %>
<h3><%= AuctionHelper.getAuctionStateText(auction.getState()) %></h3>
<% } %>

<% if (userId == auction.getCreatedBy().getId() && auction.getState() == Auction.State.Open) { %>
<form method="post" action="<%= request.getContextPath() %>/auction/close">
    <input type="hidden" name="auctionId" value="<%= auction.getId() %>">
    <button type="submit">Close Auction</button>
</form>
<% } %>

<br>
<%-- <a href="<%= request.getContextPath() %>/auction/list">Back to Auction List</a>--%>
</body>
</html>
