<nav class="navbar bg-dark bg-body-tertiary gap-3 px-2" data-bs-theme="dark">
    <a class="navbar-brand" href="<%=request.getContextPath()%>/">Auction</a>
    <form class="form-inline flex-grow-1" method="get" action="<%=request.getContextPath()%>/" style="margin-bottom: 0; display: flex;">
        <input class="form-control mr-sm-2 me-1" id="search" name="search" type="search" placeholder="Search All Auctions"
               aria-label="Search" style="max-width: 600px;">
        <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
    <div class="">
        <a class="m-0 btn btn-outline-light" href="<%=request.getContextPath()%>/auction/my-bids">My Bids</a>
    </div>
    <div class="">
        <a class="m-0 btn btn-outline-light" href="<%=request.getContextPath()%>/auction/my-auctions">My Auctions</a>
    </div>
    <form class="form-inline" method="post" action="<%=request.getContextPath()%>/account/sign-out" style="margin-bottom: 0">
        <button class="btn btn-outline-danger" type="submit">Sign Out</button>
    </form>
</nav>
