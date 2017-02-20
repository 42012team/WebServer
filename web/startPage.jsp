<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.02.2017
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="mystyle.css" rel="stylesheet">
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
            <a class="navbar-brand" href="#">S-T</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#about">О Нас</a></li>
                <li><a href="ShowAllServicesServlet">Услуги</a></li>
                <li><a href="loginPage.jsp" color="blue" class="settings">Войти</a></li>
                <li><a href="registration.jsp" color="blue" class="settings">Зарегистрироваться</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="jumbotron">
    <h1>Samara-Telecom</h1>
    <p>We specialize in blablabla</p>
</div>
</body>
</html>
