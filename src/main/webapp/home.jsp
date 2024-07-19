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
    <style>
        .auction {
            transition: background-color ease-in 200ms;
        }

        .auction:hover {
            background: var(--bs-gray-400);
        }

        .auction img {
            transition: opacity ease-in 200ms;
        }

        .auction:hover img {
            opacity: 60%;
        }
    </style>
</head>
<body>
<%@include file="components/navbar.jsp" %>

<%
    ArrayList<Auction> auctions = (ArrayList<Auction>) request.getAttribute("auctions");
%>

<div class="container ms-4 mt-5">
    <% if (auctions.isEmpty()) {%>
    <h3>No Auctions found</h3>
    <%}%>

    <a class="btn btn-outline-dark mb-4" href="<%=request.getContextPath()%>/auction/create">Create Auction</a>

    <h1 class="row mb-4">Auctions</h1>

    <% for (Auction auction : auctions) {%>
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

