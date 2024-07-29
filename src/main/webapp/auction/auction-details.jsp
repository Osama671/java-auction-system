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
    <style>
        .description {
            white-space: pre;
        }
    </style>
</head>
<body>
<%@include file="../components/navbar.jsp" %>
<%
    // Get the auction object from request attribute
    Auction auction = (Auction) request.getAttribute("auction");
    Bid highestBid = (Bid) request.getAttribute("highestBid");
    User userHighestBid = (highestBid != null) ? highestBid.getCreatedBy() : null;
    int userId = (int) request.getAttribute("userId");
    var isCreator = userId == auction.getCreatedBy().getId();
    var isOpen = auction.getState() == Auction.State.Open;
    var isEnded = auction.getState() == Auction.State.Ended || auction.getState() == Auction.State.EndedEarly;
%>

<div class="container ms-4">
    <div class="row">
        <h1 class="display-2 mt-5 col"><%=auction.getTitle()%>
        </h1>
    </div>

    <div class="row">
        <p class="lead col">
            <% if (isOpen) {%>
            <span class="text-success">Auction closes in</span>
            <span class="countdown text-success"
                  data-end-time="<%= auction.getEndsAt() %>"
                  auction-state="<%= auction.getState()%>">
            </span>
            <% } else {%>
            <%= AuctionHelper.getAuctionStateText(auction.getState()) %>
            <%}%>
        </p>
    </div>

    <div class="row g-5 mt-5">
        <img class="col-6 h-auto" src="data:image/jpeg;base64,<%=auction.getImageBase64()%>" alt="Auction Image"/>
        <div class="col-6">
            <h4 class="row">Description:</h4>
            <p class="description row"><%=auction.getDescription()%>
            </p>

            <% if (highestBid != null) { %>
            <br/>
            <h3 class="row mt-4">Highest Bid: <%=highestBid.getAmountDollars()%>
            </h3>

            <% if (highestBid.getCreatedBy().getId() == userId) {%>
            <p class="row lead fs-6 text-success">Your bid is currently the highest!</p>
            <% } %>

            <% if (isEnded && highestBid.getCreatedBy().getId() == userId) { %>
            <h3 class="row mt-5">You Won this auction!</h3>
            <% } %>

            <% if (isEnded && isCreator) { %>
            <h3 class="row mt-5">Winner Name: <%= userHighestBid.getName()  %>
            </h3>
            <h3 class="row">Winner Contact: <%= userHighestBid.getEmail() %>
            </h3>
            <% } %>
            <% } %>


            <% if (!isCreator && isOpen) { %>
            <form class="mt-5 row align-items-start" method="post">
                <input type="hidden" name="action" value="bid">
                <input class="btn btn-dark w-auto px-4" type="submit" value="Bid">
                <div class="col-6">
                    <input class="form-control" type="text" id="bid" name="bid" required>
                    <p class="lead fs-6">Minimum: <%=auction.getMinBidDollars()%>
                    </p>
                </div>
            </form>
            <% } %>

            <% if (isCreator && isOpen) { %>
            <form class="row mt-5" id="auctionForm" method="post"
                  action="<%= request.getContextPath() %>/auction/close">
                <input type="hidden" name="auctionId" value="<%= auction.getId() %>">
                <button class="btn btn-dark col-3" type="submit">Close Auction</button>
                <p class="col m-0 d-flex align-items-center">Close auction without a winner</p>
            </form>
            <% if (highestBid != null) { %>
            <form class="row" id="auctionForm" method="post"
                  action="<%= request.getContextPath() %>/auction/details?action=endAuction">
                <input type="hidden" name="id" value="<%= auction.getId() %>">
                <button class="btn btn-dark col-3" type="submit">End Auction</button>
                <p class="col m-0 d-flex align-items-center">The auction will go to the highest bidder</p>
            </form>
            <% } %>
            <% } %>

            <% var errors = (String[]) request.getAttribute("errors");
                if (errors != null && errors.length > 0) { %>
            <div class="text-danger row mt-3">
                <ul>
                    <% for (String error : errors) { %>
                    <li><%= error %>
                    </li>
                    <% } %>
                </ul>
            </div>
            <% } %>
        </div>
    </div>
</div>
</body>
</html>
