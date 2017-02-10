<%@ page import="classes.model.User" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.02.2017
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Your Profile:
<% User user = (User) request.getSession(true).getAttribute("user");%>
<p>Name :<%=user.getName()%>
</p>
<p>Surname :<%=user.getSurname()%>
</p>
<p>Email:  <%=user.getEmail()%></p>
<p>Phone : <%=user.getPhone()%>
</p>
<p>Address :<%=user.getAddress()%>
</p>
<p>Login :<%=user.getLogin()%>
</p>
<p>Password :<%=user.getPassword()%>
</p>
<form action="DispatcherServlet" method="post">
    <p><input type="submit" name="changeProfileButton" value="Изменить данные профиля">
        <input type="submit" name="link" formaction="ShowActiveServicesPage" formmethod="post"
               value="Посмотреть подключенные услуги"/>
        <input type="submit" name="addActiveService" formaction="ShowAllowedToConnectServiceServlet" formmethod="post"
               value="Подключить услугу"/>
    </p>
</form>
</body>
</html>
