<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="registrationStyle.css" rel="stylesheet">
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
                <li><a href="/WebServer_war_exploded/loginPage.jsp" color="blue" class="settings">Вход</a></li>
            </ul>
        </div>
    </div>
</nav>

<form class="registrationForm" action="/WebServer_war_exploded/SignInServlet" method="post">
    <div class="regHeader">
        <h2 class="headerText">Регистрация</h2>
    </div>
    <ul>
        <li class="regLiContainer">
            <label id="loginL" for="login"><span class="text">Логин</span></label><input type="text" name="login"
                                                                                         id="login" placeholder="Login" required>
        </li>
        <li class="regLiContainer"><label id="passwordL" for="password"><span class="text">Пароль</span></label><input
                type="password" name="password" id="password" placeholder="Password" required></li>
        <li class="regLiContainer"><label id="nameL" for="name"><span class="text">Имя</span></label><input type="text"
                                                                                                            name="name"
                                                                                                            id="name"
                                                                                                            placeholder="Имя" required/>
        </li>
        <li class="regLiContainer"><label id="surnameL" for="surname"><span class="text">Фамилия</span></label><input
                type="text" name="surname" id="surname" placeholder="Фамилия" required/></li>
        <li class="regLiContainer"><label id="emailL" for="email"><span class="text">Email</span></label><input
                type="text" name="email" id="email" placeholder="Email"/></li>
        <li class="regLiContainer"><label id="phoneL" for="phone"><span class="text">Телефон</span></label><input
                type="text" name="phone" id="phone" placeholder="Телефон"/></li>
        <li class="regLiContainer"><label id="addressL" for="address"><span class="text">Адрес</span></label><input
                type="text" name="address" id="address" placeholder="Адрес" required/></li>
    </ul>
    <input type="submit" name="loginButton" id="loginButton" value="Регистрация">

</form>
</body>
</html>
