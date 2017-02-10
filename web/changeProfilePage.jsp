<%@ page import="classes.model.User" %><%--
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
<form action="ChangeUserServlet" method="POST">
    <%User user= (User) session.getAttribute("user");%>
    <p>Name: <input type="text" name="name" value="<%=user.getName()%>"/></p>
    <p>Surname: <input type="text" name="surname" value="<%=user.getSurname()%>"/></p>
    <p>Email: <input type="text" name="email" value="<%=user.getEmail()%>"/></p>
    <p>Phone: <input type="text" value="<%=user.getPhone()%>"/></p>
    <p>Address: <input type="text" name="address" value="<%=user.getAddress()%>"/></p>
    <p>Password: <input type="text" name="password" value="<%=user.getPassword()%>"/></p>
    <p><input type="submit" name="saveChangesButton" value="Save changes"/></p>
</form>
</body>
</html>
