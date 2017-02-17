<%@ page import="classes.model.ActiveService" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.ServiceStatus" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.02.2017
  Time: 23:44
  To change this template use File | Settings | File Templates.
--%>
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
                <li><a href="allowedToConnectServicesPage.jsp" color="blue" class="settings">Посмотреть подключенные</a></li>
            </ul>
        </div>
    </div>
</nav>
<form >
    <!--<div><span class="headerText">Подключенные услуги</span></div>
<div class="showActiveService"></div>
<div class="showActiveService"></div>
<div class="showActiveService"></div>
<div class="showActiveService"></div>
<div class="showActiveService"></div>-->
    <div id="usersActiveServices"><span id="connectService"><h2>Подключенные услуги</h2></span></div>
    <ul>
        <%
            List<ActiveService> activeServiceList = (List<ActiveService>) request.getAttribute("activeServiceList");
            List<Service> serviceList = (List<Service>) request.getAttribute("activeServiceDescription");
            for (int k = 0; k < serviceList.size(); k++) {
                Service s = serviceList.get(k);
        %>

        <li class="li1" >
            <input type="radio" name="chooseActiveService" onclick="click1(this)" id="<%=activeServiceList.get(k).getId()%>" value="<%=activeServiceList.get(k).getId() %>">
            <div class="description"> Название услуги: <span class="value"><%=s.getName()%></span></div><div class="descriptionValue"> </div>
            <div  class="description">Описание услуги:<span class="value"><%=s.getDescription()%></span></div><div class="descriptionValue"> </div>
            <div  class="description">Тип услуги: <span class="value"><%=s.getType()%></span></div><div class="descriptionValue"> </div>
            <div  class="description">Статус услуги: <span class="value"><%=s.getStatus().toString()%></span></div><div class="descriptionValue"> </div>
               <%  if (activeServiceList.get(k).getNewStatus() != null) {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String strDate = sdfDate.format(activeServiceList.get(k).getDate());%>
            <div  class="description">Запланировано изменение статуса услуги на<span class="value">
    <%= activeServiceList.get(k).getNewStatus().toString()
    %> c  <%=strDate%>
                <%}%>
            </span></div><div class="descriptionValue"> </div>
            <input type="submit" style="display:none" value="Изменить" formaction="cgsfw" method="post"/><input type="submit" style="display:none" value="Удалить" formaction="DeleteActiveServiceServlet"
                                                                               method="post"/></li>
        <%}%>

    </ul>
</form>
<!--
<form>
    <%
        /*List<ActiveService> activeServiceList = (List<ActiveService>) request.getAttribute("activeServiceList");
        List<Service> serviceList = (List<Service>) request.getAttribute("activeServiceDescription");
        for (int k = 0; k < serviceList.size(); k++) {
            Service s = serviceList.get(k);*/
    %>
    <p><input type="radio" name="chooseActiveService" value="<%//=activeServiceList.get(k).getId()%>"/> Услуга номер
        : <//%=k + 1%>
    </p>

    <p>Название услуги: <%//=s.getName()%>
    </p>
    <p>Описание услуги: <%//=s.getDescription()%>
    </p>
    <p>Тип услуги:<%//=s.getType()%>
    </p>
    <p>Статус услуги:<%//=activeServiceList.get(k).getCurrentStatus().toString()%>
    </p>
    <% //if (s.getStatus().equals(ServiceStatus.DEPRECATED)) {%>
    <p> Ваша услуга устарела!
    <p>
            <%/* }
        if (activeServiceList.get(k).getNewStatus() != null) {

            SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String strDate = sdfDate.format(activeServiceList.get(k).getDate());*/%>
    <p> Запланировано изменение статуса услуги на <%//= activeServiceList.get(k).getNewStatus().toString()
    %> c  <%//=strDate%>
    </p>
    <%
      //  }
      //  StringBuffer stringBuffer=new StringBuffer()
    %>
    <input type="hidden" name="<%//=activeServiceList.get(k).getId()%>" value=""/>

    <p>________________________________________________________</p>
    <%
     //   }
    %>
    <input type="submit" name="deleteActiveServiceButton" formaction="DeleteActiveServiceServlet" formmethod="post"
           value="Удалить услугу"/>
    <input type="submit" name="changeActiveService" formaction="ChangeActiveServiceServlet" formmethod="post"
           value="Изменить выбранную услугу"/>
    <input type="submit" name="addActiveService" formaction="ShowAllowedToConnectServiceServlet" formmethod="post"
           value="Подключить услугу"/>
    <input type="submit" name="inProfile" formaction="ShowProfilePageServlet" formmethod="post"
           value="Вернуться в профиль"/>
</form>
-->
</body>
</html>
