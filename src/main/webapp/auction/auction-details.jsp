<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Auction" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Bid" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.helper.AuctionHelper" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auction Details</title>
    <%@include file="/common.jsp" %>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/countdown.js" defer></script>
    <script type="text/javascript">
        function setFormAction(actionUrl) {
            document.getElementById('auctionForm').action = actionUrl;
        }
    </script>
</head>
<body>
<%@include file="../components/navbar.jsp" %>
<%
    // Get the auction object from request attribute
    Auction auction = (Auction) request.getAttribute("auction");
    Bid highestBid = (Bid) request.getAttribute("highestBid");
    User userHighestBid = (highestBid != null) ? (User) highestBid.getCreatedBy() : null;
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
Current Max Bid: <%= highestBid == null ? "No bids" : highestBid.getAmount() / 100F%><br/>
Status: <%=auction.getState()%><br/>
Closes At: <%=auction.getEndsAt()%><br/>
<% if (auction.getImageBase64() != null) { %>
<img src="data:image/jpeg;base64,<%=auction.getImageBase64()%>" alt="Auction Image" height="500px" width="500px"/>
<% } %>

<% if (auction.getState() == Auction.State.Open) {%><p class="countdown" data-end-time="<%= auction.getEndsAt() %>"
                                                       auction-state="<%= auction.getState()%>"></p><%}%>

<% if (auction.getCreatedBy().getId() == userId) { %>
<h3>You can't bid on your listing</h3>
<% } else if (auction.getState() == Auction.State.Open) { %>
<form method="post" action="<%= request.getContextPath() %>/auction/details">
    <label for="bid">Bid:</label>
    <input type="text" id="bid" name="bid" required><br><br>
    <input type="hidden" id="auctionId" name="auctionId" value="<%= auction.getId() %>">
    <input type="hidden" name="action" value="bid">

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

<% if (userId == auction.getCreatedBy().getId() && auction.getState() == Auction.State.Open && highestBid != null) { %>
<form id="auctionForm" method="post">
    <input type="hidden" name="auctionId" value="<%= auction.getId() %>">
    <button type="submit" onclick="setFormAction('<%= request.getContextPath() %>/auction/close')">Close Auction</button>
    <button type="submit" onclick="setFormAction('<%= request.getContextPath() %>/auction/details?action=endAuction')">End Auction</button>
</form>
<% } %>

<% if ( (auction.getState() == Auction.State.Ended || auction.getState() == Auction.State.EndedEarly) && userId == auction.getCreatedBy().getId() && highestBid != null) { %>
<h2>Bidder Name: <%= userHighestBid.getName()  %></h2>
<h2>Bidder Contact: <%= userHighestBid.getEmail() %></h2>
<% } %>

<br>
<%-- <a href="<%= request.getContextPath() %>/auction/list">Back to Auction List</a>--%>
</body>
</html>
