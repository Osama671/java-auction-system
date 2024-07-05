<nav class="navbar bg-dark navbar-expand-lg bg-body-tertiary" data-bs-theme="dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="<%=request.getContextPath()%>/">Auction</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <form method="get" action="<%=request.getContextPath()%>/"  class="form-inline my-2 my-lg-0" style="display: flex;">
                <input class="form-control mr-sm-2" id="search" name="search" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
            <form method="post" action="<%=request.getContextPath()%>/account/sign-out">
                <button class="btn btn-outline-danger" type="submit">Sign Out</button>
            </form>
        </div>
    </div>
</nav>
