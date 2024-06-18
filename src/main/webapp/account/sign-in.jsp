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
</head>
<body>
<h2>Sign In</h2>

<form method="post">
    <label for="email">Email:</label>
    <input type="text" id="email" name="email" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

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

    <input type="submit" value="Sign In">
</form>

<p>
    Don't have an account? <a href="<%=request.getContextPath()%>/account/register">Register</a>
</p>
</body>
</html>
