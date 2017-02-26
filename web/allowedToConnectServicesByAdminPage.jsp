<%@ page import="classes.model.Service" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="showActiveServicesStyle.css" rel="stylesheet">
    <script src="addActiveServiceJs.js"></script>
</head>
<body onload="load()">
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
                <li><a href="javascript:history.back();"><span class="glyphicon glyphicon-arrow-right">Назад</span></a>
                </li>
                <li><a href="startPage.jsp">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>

<form action="/AddActiveServiceByAdminServlet" method="post">

    <div id="usersActiveServices"><span id="connectService"><h2>Подключить услугу</h2></span></div>

    <ul>
        <div class="container">
            <div class="row">
                <%
                    List<Service> serviceList = (List<Service>) request.getAttribute("allowedToConnectServices");
                    for (Service s : serviceList) {
                %>
                <div class="col-md-4 text-center">
                    <div class="box">
                        <div class="box-content">
                            <h2 class="tag-title"><span class="value"><%=s.getName()%></span></h2>
                            <hr/>
                            <li class="li1">
                                <input type="radio" class="radio" name="serviceId" onclick="click1(this)"
                                       id="<%=s.getId()%>" value="<%=s.getId()%>">
                                <div class="description">Описание услуги:<span
                                        class="value"><%=s.getDescription()%></span></div>
                                <div class="description">Тип услуги: <span class="value"><%=s.getType()%></span></div>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=s.getStatus().toString()%></span></div>
                                <input type="datetime-local" class="dateField" style="display:none"
                                       name="activationDate<%=s.getId()%>"><input type="submit" class="addButton"
                                                                                  style="display:none"
                                                                                  value="Добавить"/></li>
                            <br/>
                        </div>
                    </div>
                </div>
                <%}%>
            </div>
        </div>
    </ul>
</form>

</body>
</html>