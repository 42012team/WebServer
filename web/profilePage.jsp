<%--
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
<p>Name :${name}</p>
<p>Surname :${surname}</p>
<p>Email: ${email}</p>
<p>Phone :${phone}</p>
<p>Address :${address}</p>
<p>Login :${login}</p>
<p>Password :${password}</p>
<form action="DispatcherServlet" method="post">
<p><input type="submit" name="changeProfileButton" value="changeProfile"> </p>
 <input type="submit" name="link" formaction="ShowActiveServicesPage" formmethod="post" value="Посмотреть подключенные услуги"/>
    <input type="submit" name="addActiveService" formaction="ShowAllowedToConnectServiceServlet" formmethod="post" value="Подключить услугу"/>
</form>
</body>
</html>
