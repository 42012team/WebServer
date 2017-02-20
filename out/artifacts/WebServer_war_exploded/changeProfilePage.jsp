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
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="changeProfileStyle.css" rel="stylesheet">
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

<form class="changeForm" action="ChangeUserServlet" method="POST">
    <div class="regHeader">
        <h2 class="headerText">Редактирование</h2>
    </div>
    <ul>
       <% User user= (User) session.getAttribute("user");%>
        <li class="changeContainer"><label id="passwordL" for="password"><span class="text">Пароль</span></label><input
                type="password" name="password" id="password" value="<%=user.getPassword()%>"></li>
        <li class="changeContainer"><label id="nameL" for="name"><span class="text">Имя</span></label><input type="text"
                                                                                                            name="name"
                                                                                                            id="name"
                                                                                                            value="<%=user.getName()%>"/>
        </li>
        <li class="changeContainer"><label id="surnameL" for="surname"><span class="text">Фамилия</span></label><input
                type="text" name="surname" id="surname"  value="<%=user.getSurname()%>"/></li>
        <li class="changeContainer"><label id="emailL" for="email"><span class="text">Email</span></label><input
                type="text" name="email" id="email"  value="<%=user.getSurname()%>"/></li>
        <li class="changeContainer"><label id="phoneL" for="phone"><span class="text">Телефон</span></label><input
                type="text" name="phone" id="phone" value="<%=user.getPhone()%>"/></li>
        <li class="changeContainer"><label id="addressL" for="address"><span class="text">Адрес</span></label><input
                type="text" name="address" id="address"  value="<%=user.getAddress()%>"/></li>
    </ul>

    <input type="submit" name="saveButton" id="saveButton" value="Сохранить">
</form>

</body>
</html>
