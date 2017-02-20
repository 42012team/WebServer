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
<form>
    <div class="col-md-6 text-center">
        <div class="box">
            <div class="box-content">
                <h2>Добавление услуги</h2>
                <p>Название услуги:<input type="text" name="name" value=""></p>
                <p>Описание услуги:<input type="text" name="description" value=""></p>
                <p>Тип услуги:<input type="text" name="type" value=""></p>
                <input type="submit" formaction="/AddServiceServlet" formmethod="post" value="Добавить услугу"/>
            </div>
        </div>
    </div>
</form>
</body>
</html>
