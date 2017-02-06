<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.02.2017
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="Servlet" method="POST">
    <p>Name: <input type="text" name="name"/></p>
    <p>Surname: <input type="text" name="surname"/></p>
    <p>Email: <input type="text" name="email"/></p>
    <p>Phone: <input type="text" name="phone"/></p>
    <p>Address: <input type="text" name="address"/></p>
    <p>Password: <input type="text" name="password"/></p>
    <p><input type="submit" name="saveChangesButton" value="Save changes"/></p>
</form>
</body>
</html>
