<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Auction" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.Bid" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.helper.AuctionHelper" %>
<%@ page import="ca.lambtoncollege.fsdm.s24.auction.model.User" %>
<%@ page import="java.util.ArrayList" %>
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
    int userId = (int) request.getAttribute("userId");
%>

<div class="container ms-4 mt-5">
    <% if (auctions.isEmpty()) {%>
    <h3>You don't have any auctions</h3>
    <%}%>
    <h1 class="row mb-4">My Auctions</h1>


    <% for (Auction auction : auctions) { %>
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
                        <% if (auction.getState() == Auction.State.Open) {%>
                        <small class="card-text text-success">Auction closes in</small>
                        <small class="countdown card-text text-success"
                               data-end-time="<%= auction.getEndsAt() %>"
                               auction-state="<%= auction.getState()%>">
                        </small><%
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
</div>


</body>
</html>
