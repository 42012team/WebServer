<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.02.2017
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="SignInServlet" method="POST">
<p>Name: <input type="text" name="name"/></p>
<p>Surname: <input type="text" name="surname"/></p>
<p>Email: <input type="text" name="email"/></p>
<p>Phone: <input type="text" name="phone"/></p>
<p>Address: <input type="text" name="address"/></p>
<p>Login: <input type="text" name="login"/></p>
<p>Password: <input type="text" name="password"/></p>
<p><input type="submit" name="signInButton" value="signIn"/></p>
</form>
</body>
</html>
