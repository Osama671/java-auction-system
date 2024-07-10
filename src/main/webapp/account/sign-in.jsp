<%--
  Created by IntelliJ IDEA.
  User: george
  Date: 6/17/24
  Time: 9:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
    <%@include file="/common.jsp" %>
    <style>
        .card {
            max-width: 572px;
        }
    </style>
</head>
<body class="container h-100 d-flex flex-column justify-content-center align-items-center">
<div class="card w-100 p-5 border-0 shadow-lg">
    <h1 class="mb-5 display-6">Sign In</h1>

    <form class="d-flex flex-column align-items-center" method="post">
        <div class="container mb-4">
            <div class="row mb-2">
                <label class="col-3 m-auto" for="email">Email:</label>
                <input class="col-9" type="text" id="email" name="email" required><br><br>
            </div>

            <div class="row">
                <label class="col-3 m-auto" for="password">Password:</label>
                <input class="col-9" type="password" id="password" name="password" required><br><br>
            </div>
        </div>

        <%
            var errors = (String[]) request.getAttribute("errors");
            if (errors != null && errors.length > 0) {
        %>
        <div class="text-danger align-self-start">
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

        <input class="btn btn-dark mt-3 fs-5" type="submit" value="Sign In">
    </form>

    <p class="m-0 mt-4">
        Don't have an account? <a href="<%=request.getContextPath()%>/account/register">Register</a>
    </p>
</div>
</body>
</html>
