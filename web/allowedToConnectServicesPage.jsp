<%@ page import="java.util.List" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="showActiveServiceStyle.css" rel="stylesheet">
    <script src="myjs.js"></script>
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
                <li><a href="#services">Услуги</a></li>
                <li><a href="#signin" color="blue" class="settings">Вернуться в профиль</a></li>
                <li><a href="#signin" color="blue" class="settings">Посмотреть подключенные</a></li>
            </ul>
        </div>
    </div>
</nav>

<form action="AddActiveServiceServlet" method="post">

    <div id="usersActiveServices"><span id="connectService"><h2>Подключить услугу</h2></span></div>
    <ul>
        <%
           List<Service> serviceList = (List<Service>) request.getAttribute("allowedToConnectServices");
            System.out.println(serviceList.size()+"size");
                for (Service s : serviceList) {
        %>

        <li class="li1" >
            <input type="radio"  name="serviceId" onclick="click1(this)" id="<%=s.getId()%>" value="<%=s.getId()%>" >
            <div class="description"> Название услуги: <span class="value"><%=s.getName()%></span></div><div class="descriptionValue"> </div>
            <div  class="description">Описание услуги:<span class="value"><%=s.getDescription()%></span></div><div class="descriptionValue"> </div>
            <div  class="description">Тип услуги: <span class="value"><%=s.getType()%></span></div><div class="descriptionValue"> </div>
            <div  class="description">Статус услуги: <span class="value"><%=s.getStatus().toString()%></span></div><div class="descriptionValue"> </div>

            </span></div><div class="descriptionValue"> </div>
            <input type="submit" style="display:none" value="Добавить"/></li>
        <%}%>

    </ul>
</form>
<!--</head>
<body>
<form action="AddActiveServiceServlet" method="post">
    <% //List<Service> serviceList = (List<Service>) request.getAttribute("allowedToConnectServices");
      //  int j = 1;
    //    User user = (User) request.getAttribute("user");
    //    for (Service s : serviceList) {%>
    <p>_______________________________________________________</p>
    <p><input type="radio" name="serviceId" value="<%//=s.getId()%>"/> Услуга номер <%//=j%>
    </p>
    <p>Название услуги:<%//=s.getName()%>
    </p>
    <p>Описание услуги:<%//=s.getDescription()%>
    </p>
    <p>Тип услуги:<%//=s.getType()%>
    <p>_______________________________________________________</p>


    <%// j++;
   // }
    %>
    <p>Введите дату подключения: <input type="text" name="activationDate"/></p>
    <input type="submit" name="addActiveService" value="Добавить"/>
    <input type="submit" name="inProfile" formaction="ShowProfilePageServlet" formmethod="post"
           value="Вернуться в профиль"/>
</form>-->
</body>
</html>
