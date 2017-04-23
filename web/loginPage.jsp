<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="loginStyle.css" rel="stylesheet">
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
                <li><a href="javascript:history.back();"><span class="glyphicon glyphicon-arrow-right">Назад</span></a>
                </li>
                <li><a href="/WebServer_war_exploded/ShowAllServicesServlet">Все услуги</a></li>
                <li><a href="/WebServer_war_exploded/registration.jsp" color="blue" class="settings">Регистрация</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<form class="loginForm" action="/WebServer_war_exploded/LogInServlet" method="post">
    <div class="loginHeader">
        <h2 class="headerText">Авторизация</h2>
    </div>

    <input type="text" name="login" id="login" placeholder="Login" required>
    <input type="password" name="pass" id="password" placeholder="Password" required>
    <input type="submit" name="loginButton" id="loginButton" value="Вход">
</form>
</body>
</html>
