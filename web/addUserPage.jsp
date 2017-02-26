<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="servicePageStyle.css" rel="stylesheet">
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
<form action="/AddUserServlet" method="POST">
    <div class="col-md-6 text-center">
        <div class="box">
            <div class="box-content">
                <h2>Добавление пользователя</h2>
                <p>Login: <input type="text" name="login"/></p>
                <p>Password: <input type="text" name="password"/></p>
                <p>Имя: <input type="text" name="name"/></p>
                <p>Фамилия: <input type="text" name="surname"/></p>
                <p>Email: <input type="text" name="email"/></p>
                <p>Телефон: <input type="text" name="phone"/></p>
                <p>Адрес: <input type="text" name="address"/></p>
                <p>Привилегированность: <input type="radio" name="privilege" value="admin">ADMIN <input type="radio" name="privilege" value="user">USER</p>
                <p><input type="submit" value="Добавить"/></p>
            </div>
        </div>
    </div>
</form>
</body>
</html>
