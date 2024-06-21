<%--
  Created by IntelliJ IDEA.
  User: george
  Date: 6/19/24
  Time: 11:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/common.jsp" %>
</head>
<body>
<div class="container-fluid form-container p-4">
    <form method="post">
        <%
            var title = request.getAttribute("title") == null ? "" : request.getAttribute("title");
            var description = request.getAttribute("description") == null ? "" : request.getAttribute("description");
            var minBid = request.getAttribute("minBid") == null ? "" : request.getAttribute("minBid");
            var endDate = request.getAttribute("endDate") == null ? "" : request.getAttribute("endDate");
        %>

        <div class="mb-3">
            <label for="title" class="form-label">Title</label>
            <input type="text" class="form-control" id="title" name="title" aria-describedby="emailHelp"
                   placeholder="Title" value="<%=title%>">
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" aria-describedby="emailHelp"
                      placeholder="Description"><%=description%></textarea>
        </div>
        <div class="input-group mb-3">
            <span class="input-group-text" id="dollar">$</span>
            <input type="text" class="form-control" placeholder="Minimum Bid" name="minBid" aria-label="Minimum Bid"
                   aria-describedby="dollar" value="<%=minBid%>">
        </div>
        <div class="mb-3">
            <label for="endDate" class="form-label">End Date</label>
            <input class="form-control" id="endDate" name="endDate" aria-describedby="emailHelp"
                   placeholder="YYYY/MM/DD hh:mm:ss" value="<%=endDate%>">
        </div>

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
        <%
            }
        %>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>
