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
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="profileStyle.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Samara-Telecom</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#about">О Нас</a></li>
                <li><a href="#services">Услуги</a></li>
                <li><a href="/ShowActiveServicesPage" color="blue" class="settings">Управление услугами</a></li>
            </ul>
        </div>
    </div>
</nav>
<form class="profileForm" action="changeProfilePage.jsp" method="post">
    <div class="regHeader">
        <h2 class="headerText">Личные данные</h2>
    </div>
    <ul>
        <% User user = (User) request.getSession(true).getAttribute("user");%>
        <li class="profileContainer">
            <label id="loginL"><span class="text">Логин:</span></label> <label id="loginV"><span
                class="text"><%=user.getLogin()%></span></label>
        </li>
        <li class="profileContainer"><label id="passwordL"><span class="text">Пароль:</span></label> <label
                id="passwordV"><span class="text"><%=user.getPassword()%></span></label></li>
        <li class="profileContainer"><label id="nameL"><span class="text">Имя:</span></label> <label id="nameV"><span
                class="text"><%=user.getName()%></span></label>
        </li>
        <li class="profileContainer"><label id="surnameL"><span class="text">Фамилия:</span></label> <label
                id="surnameV"><span class="text"><%=user.getSurname()%></span></label></li>
        <li class="profileContainer"><label id="emailL"><span class="text">Email:</span></label> <label
                id="emailV"><span class="text"><%=user.getEmail()%></span></label></li>
        <li class="profileContainer"><label id="phoneL"><span class="text">Телефон:</span></label> <label
                id="phoneV"><span class="text"><%=user.getPhone()%></span></label></li>
        <li class="profileContainer"><label id="addressL"><span class="text">Адрес:</span></label> <label id="addressV"><span
                class="text"><%=user.getAddress()%></span></label></li>
    </ul>
    <input type="submit" name="loginButton" id="loginButton" value="Изменить данные">

</form>
<!--Your Profile:


<%// User user = (User) request.getSession(true).getAttribute("user");%>
<p>Name :<%//=user.getName()%>
</p>
<p>Surname :<%//=user.getSurname()%>
</p>
<p>Email:  <%//=user.getEmail()%></p>
<p>Phone : <%//=user.getPhone()%>
</p>
<p>Address :<%//=user.getAddress()%>
</p>
<p>Login :<%//=user.getLogin()%>
</p>
<p>Password :<%//=user.getPassword()%>
</p>
<form action="DispatcherServlet" method="post">
    <p><input type="submit" name="changeProfileButton" value="Изменить данные профиля">
        <input type="submit" name="link" formaction="ShowActiveServicesPage" formmethod="post"
               value="Посмотреть подключенные услуги"/>
        <input type="submit" name="addActiveService" formaction="ShowAllowedToConnectServiceServlet" formmethod="post"
               value="Подключить услугу"/>
    </p>
</form>-->
</body>
</html>
