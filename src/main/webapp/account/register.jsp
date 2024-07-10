<%--
  Created by IntelliJ IDEA.
  User: george
  Date: 6/14/24
  Time: 9:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Register</title>
    <%@include file="/common.jsp" %>
    <style>
        .card {
            max-width: 572px;
        }
    </style>
</head>
<body class="container h-100 d-flex flex-column justify-content-center align-items-center">
<div class="card w-100 p-5 border-0 shadow-lg">
    <h1 class="mb-5 display-6">Create an Account</h1>

    <form class="d-flex flex-column align-items-center" method="post">
        <%
            var email = request.getAttribute("email") == null ? "" : request.getAttribute("email");
            var password = request.getAttribute("password") == null ? "" : request.getAttribute("password");
            var name = request.getAttribute("name") == null ? "" : request.getAttribute("name");
        %>

        <div class="container mb-4">
            <div class="row mb-2">
                <label class="col-4 m-auto" for="name">Name:</label>
                <input class="col-8" type="text" id="name" name="name" required value=<%=name%>><br><br>
            </div>

            <div class="row mb-2">
                <label class="col-4 m-auto" for="email">Email:</label>
                <input class="col-8" type="text" id="email" name="email" required value=<%=email%>><br><br>
            </div>

            <div class="row mb-2">
                <label class="col-4 m-auto" for="password">Password:</label>
                <input class="col-8" type="password" id="password" name="password" required value=<%=password%>><br><br>
            </div>

            <div class="row mb-2">
                <label class="col-4 m-auto" for="confirmPassword">Confirm Password:</label>
                <input class="col-8" type="password" id="confirmPassword" name="confirmPassword" required><br><br>
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

        <input class="btn btn-dark mt-3 fs-5" type="submit" value="Register">
    </form>

    <p class="m-0 mt-4">
        Already have an account? <a href="<%=request.getContextPath()%>/account/sign-in">Sign In</a>
    </p>
</div>
</body>
</html>
