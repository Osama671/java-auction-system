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
</head>
<body>
<h2>Create an Account</h2>
<form method="post">
    <%
        var email = request.getAttribute("email") == null ? "" : request.getAttribute("email");
        var password = request.getAttribute("password") == null ? "" : request.getAttribute("password");
        var name = request.getAttribute("name") == null ? "" : request.getAttribute("name");
    %>

    <label for="email">Email:</label>
    <input type="text" id="email" name="email" required value=<%=email%>><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required value=<%=password%>><br><br>

    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required value=<%=name%>><br><br>

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

    <input type="submit" value="Register">
</form>

<p>
    Already have an account? <a href="<%=request.getContextPath()%>/account/sign-in">Sign In</a>
</p>
</body>
</html>
