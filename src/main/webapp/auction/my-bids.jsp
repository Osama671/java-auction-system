<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Auction" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.helper.AuctionHelper" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Bid" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.repository.BidRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <%@include file="/common.jsp" %>
    <script type="text/javascript" src="<%= request.getContextPath() %>/js/countdown.js" defer></script>

</head>
<body>
<%@include file="../components/navbar.jsp" %>


<%
    ArrayList<Auction> auctions = (ArrayList<Auction>) request.getAttribute("auctions");
    ArrayList<Bid> bids = (ArrayList<Bid>) request.getAttribute("bids");
    int userId = (int) request.getAttribute("userId");
%>

<div class="container ms-4 mt-5">
    <% if (bids.isEmpty()) {%>
    <h3>You don't have any bids</h3>
    <%}%>

    <% if (!bids.isEmpty()) { %>
    <h1 class="row mb-4">My bids</h1>
    <% } %>


    <% for (Auction auction : auctions) { %>
    <% for (Bid bid: bids) { %>
    <% if (userId == bid.getCreatedBy().getId() && auction.getId() == bid.getAuctionId()) { %>
    <div class="row">
        <a class="auction card col-8 mb-4 p-0 shadow text-decoration-none border-0"
           href="<%=request.getContextPath() + "/auction/details?id=" + auction.getId()%>">
            <div class="row g-0">
                <div class="col-4">
                    <img class="w-100 h-auto rounded-start" src="data:image/jpeg;base64,<%=auction.getImageBase64()%>"
                         alt="Auction Image">
                </div>
                <div class="col-8">
                    <div class="card-body">
                        <h3 class="card-title"><%= auction.getTitle() %>
                        </h3>
                        <h3>Your current bid: <%= bid.getAmountDollars() %></h3>
                        <% if (auction.getState() == Auction.State.Open) {%>
                        <small class="card-text text-success">Auction closes in</small>
                        <small class="countdown card-text text-success"
                               data-end-time="<%= auction.getEndsAt() %>"
                               auction-state="<%= auction.getState()%>">
                        </small>
                        <% if (bid.getAmount() != BidRepository.getHighestBid(auction.getId()).getAmount()) { %>
                        <p class="text-danger mt-5 fs-2 fw-bold text-center"> You have been outbid! </p>
                        <p class="text-danger text-end align-bottom mt-4">Highest bid: <span class="fst-italic fw-bold"> <%= BidRepository.getHighestBid(auction.getId()).getAmountDollars() %></span></p>
                        <% } %>
                        <%
                    } else {%>
                        <small class="card-text text-secondary"><%= AuctionHelper.getAuctionStateText(auction.getState()) %>
                        </small>
                        <%}%>
                    </div>
                </div>
            </div>
        </a>
    </div>
    <% } %>
    <% } %>
    <% } %>
</div>


</body>
</html>
