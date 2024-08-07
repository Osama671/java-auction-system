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
    <title>Create Auction</title>
    <%@include file="/common.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
            crossorigin="anonymous"></script>
    <!-- Tempus Dominus JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/@eonasdan/tempus-dominus@6.9.4/dist/js/tempus-dominus.min.js"
            crossorigin="anonymous"></script>

    <!-- Tempus Dominus Styles -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/@eonasdan/tempus-dominus@6.9.4/dist/css/tempus-dominus.min.css"
          crossorigin="anonymous">
    <style>
        .uploaded-image-preview {
            max-width: 100%;
            height: auto;
            margin-top: 10px;
        }

        .form-container {
            max-width: 600px;
            margin: auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .input-group-text {
            border-top-right-radius: 0;
            border-bottom-right-radius: 0;
        }
    </style>
</head>
<body>
<%@include file="../components/navbar.jsp" %>
<div class="container-fluid py-5">
    <div class="form-container">
        <h2 class="mb-4">Create Auction</h2>
        <form method="post" enctype="multipart/form-data">
            <%
                var title = request.getAttribute("title") == null ? "" : request.getAttribute("title");
                var description = request.getAttribute("description") == null ? "" : request.getAttribute("description");
                var minBid = request.getAttribute("minBid") == null ? "" : request.getAttribute("minBid");
                var endDate = request.getAttribute("endDate") == null ? "" : request.getAttribute("endDate");
                var auctionImage = request.getAttribute("auctionImage") == null ? "" : request.getAttribute("auctionImage");
            %>

            <div class="form-group">
                <label for="title" class="form-label">Title</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="Title" value="<%=title%>">
            </div>
            <div class="form-group">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description"
                          placeholder="Description"><%=description%></textarea>
            </div>
            <div class="form-group">
                <label for="minBid" class="form-label">Minimum Bid</label>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="dollar">$</span>
                    </div>
                    <input type="text" class="form-control" placeholder="Minimum Bid" id="minBid" name="minBid"
                           aria-label="Minimum Bid" aria-describedby="dollar" value="<%=minBid%>">
                </div>
            </div>
            <div class="form-group">
                <label for="endDate" class="form-label">Picker</label>
                <div class="input-group log-event" id="datetimepicker" data-td-target-input="nearest" data-td-target-toggle="nearest" data-td-target="#datetimepicker" data-td-toggle="datetimepicker">
                    <span class="input-group-text" data-td-target="#datetimepicker" data-td-toggle="datetimepicker">
                         <i class="fas fa-calendar"></i>
                    </span>
                    <input id="endDate" name="endDate" type="text" class="form-control" data-td-target="#endDate"/>
                </div>
                <%--                <label for="endDate" class="form-label">End Date</label>--%>
                <%--                <div class="input-group date" id="datetimepicker" data-target-input="nearest">--%>
                <%--                    <input type="text" class="form-control datetimepicker-input" id="endDate" name="endDate" placeholder="YYYY/MM/DD hh:mm:ss" data-target="#datetimepicker" value="<%=endDate%>"/>--%>
                <%--                    <div class="input-group-append" data-target="#datetimepicker" data-toggle="datetimepicker">--%>
                <%--                        <div class="input-group-text"><i class="fa fa-calendar"></i></div>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
            </div>
            <div class="form-group">
                <label for="auctionImage" class="form-label">Upload Image</label>
                <input type="file" class="form-control-file" id="auctionImage" name="auctionImage">
                <%
                    session = request.getSession();
                    String uploadedImage = (String) session.getAttribute("uploadedImage");
                    if (uploadedImage != null && !uploadedImage.isEmpty()) {
                %>
                <img src="data:image/jpeg;base64,<%=uploadedImage%>" class="uploaded-image-preview"/>
                <% } %>
            </div>

            <%
                var errors = (String[]) request.getAttribute("errors");
                if (errors != null && errors.length > 0) {
            %>
            <div class="alert alert-danger">
                <ul class="mb-0">
                    <% for (String error : errors) { %>
                    <li><%= error %>
                    </li>
                    <% } %>
                </ul>
            </div>
            <%
                }
            %>

            <button type="submit" class="btn btn-primary btn-block">Submit</button>
        </form>
    </div>
</div>
<script>
    const picker = new tempusDominus
        .TempusDominus(document.getElementById('datetimepicker'), {
            localization: {
                hourCycle: 'h23',
                dateFormats: {
                    LLL: "yyyy/MM/dd HH:mm:ss"
                },
                format: "LLL",
            }
        });
</script>
</body>
</html>
