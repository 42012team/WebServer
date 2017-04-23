<%@ page import="classes.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="searchForm.css" rel="stylesheet">
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
                <li><a href="/WebServer_war_exploded/ShowAdminPageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <li><a href="/WebServer_war_exploded/loginPage.jsp" color="blue" class="settings">Вход</a></li>
            </ul>
        </div>
    </div>
</nav>

<form class="registrationForm" action="/WebServer_war_exploded/SearchUserServlet" method="post" style="height: 65%">
    <div class="regHeader">
        <h2 class="headerText">Поиск</h2>
    </div>
    <ul>
        <li class="regLiContainer">
            <label id="loginL" for="login"><span class="text">Логин</span></label><input type="text" name="login"
                                                                                         id="login" placeholder="Login">
        </li>
        <li class="regLiContainer"><label id="nameL" for="name"><span class="text">Имя</span></label><input type="text"
                                                                                                            name="name"
                                                                                                            id="name"
                                                                                                            placeholder="Имя"/>
        </li>
        <li class="regLiContainer"><label id="surnameL" for="surname"><span class="text">Фамилия</span></label><input
                type="text" name="surname" id="surname" placeholder="Фамилия"/></li>
        <li class="regLiContainer"><label id="emailL" for="email"><span class="text">Email</span></label><input
                type="text" name="email" id="email" placeholder="Email"/></li>
        <li class="regLiContainer"><label id="phoneL" for="phone"><span class="text">Телефон</span></label><input
                type="text" name="phone" id="phone" placeholder="Телефон"/></li>
        <li class="regLiContainer"><label id="addressL" for="address"><span class="text">Адрес</span></label><input
                type="text" name="address" id="address" placeholder="Адрес"/></li>
    </ul>
    <br/>
    <input type="checkbox"  id="check" class="checkboxChoose" name="bySubparams" value="0"><label class="L" for="check">Поиск по подстроке</label>
    <input type="submit" name="loginButton" id="loginButton" value="Поиск">
</form>
</body>
</html>
