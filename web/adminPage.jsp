<%@ page import="classes.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="adminStyle.css" rel="stylesheet">
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
            </ul>
        </div>
    </div>
</nav>
<form>
    <div class="col-md-6 text-center">
        <div class="box">
            <div class="box-content">
                <h2>Администратор</h2>
                <% User user = (User) request.getSession(true).getAttribute("user");

                %>
                <div class="adminLogin"> <p id="login">Логин:<%=user.getLogin()%></p></div>
                <p id="password">Пароль:<%=user.getPassword()%></p>
                <input type="submit" formaction="/AllServicesServlet" formmethod="post" value="Посмотреть все услуги компании"/>
                <input type="submit" formaction="/addAdminPage.jsp" formmethod="post" value="Добавить администратора"/>
                <input type="submit" formaction="/GetAllUsersServlet" formmethod="post" value="AllUsers"/>
            </div>
        </div>
    </div>
</form>
</body>
</html>
