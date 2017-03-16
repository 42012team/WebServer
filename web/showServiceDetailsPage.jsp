<%@ page import="classes.model.Service" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="servicesStyle.css" rel="stylesheet">
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
            </ul>
        </div>
    </div>
</nav>
<%
    Service service = (Service) request.getAttribute("service");
%>
<form>
    <div class="col-md-4 text-center" style="margin-left: 33%; margin-top: 12%">
        <div class="box">
            <div class="box-content">
                <h1 class="tag-title">
                    <%= service.getName()%>
                </h1>
                <hr/>
                <p> Описание услуги: <%= service.getDescription()%>
                </p>
                <p> Тип услуги: <%= service.getType()%>
                </p>
                <p>Статус услуги: <%= service.getStatus()%>
                </p>
            </div>
        </div>
    </div>
</form>
</body>
</html>

