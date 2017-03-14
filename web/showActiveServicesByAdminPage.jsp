<%@ page import="classes.model.ActiveService" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="showActiveServicesStyle.css" rel="stylesheet">
    <script src="activeServiscesEffect.js"></script>
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
                <li><a href="javascript:history.back();"><span class="glyphicon glyphicon-arrow-right">Назад</span></a>
                </li>
                <li><a href="/ShowAllServicesServlet">Все услуги</a></li>
                <li><a href="/ShowAllowedToConnectServicesByAdminServlet" color="blue" class="settings">Подключить
                    услугу</a></li>
                <li><a href="/ShowAdminPageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <li><a href="/startPage.jsp" color="blue" class="settings">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>
<form method="post">
    <div id="usersActiveServices"><span
            id="connectService"><h2>Подключенные услуги пользователя с ID <%=session.getAttribute("userForChange")%></h2></span>
    </div>
    <ul>
        <div class="container">
            <div class="row">

                <%
                    List<ActiveService> activeServiceList = (List<ActiveService>) request.getAttribute("activeServiceList");
                    List<Service> serviceList = (List<Service>) request.getAttribute("activeServiceDescription");
                    for (int k = 0; k < serviceList.size(); k++) {
                        Service s = serviceList.get(k);
                %>
                <div class="col-md-4 text-center">
                    <div class="box">
                        <div class="box-content">
                            <h2 class="tag-title"><span class="value"><%=s.getName()%></span></h2>
                            <hr/>
                            <li>
                                <input type="radio" class="radio" name="chooseActiveService" onclick="click1(this)"
                                       id="<%=activeServiceList.get(k).getId()%>"
                                       value="<%=activeServiceList.get(k).getId()%>">
                                <div class="description">Описание услуги:<span
                                        class="value"><%=s.getDescription()%></span>
                                </div>
                                <div class="description">Тип услуги: <span class="value"><%=s.getType()%></span></div>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=activeServiceList.get(k).getFirstStatus().toString()%></span>
                                </div>
                                <% if (activeServiceList.get(k).getSecondStatus() != null) {
                                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    String strDate = sdfDate.format(activeServiceList.get(k).getDate());%>
                                <div class="description">Запланировано изменение статуса услуги на<span class="value">
    <%= activeServiceList.get(k).getSecondStatus().toString()
    %> c  <%=strDate%>

            </span></div>
                                <%} else {%>
                                <br/>
                                <br/>
                                <%}%>

                                <input type="submit" class="changeButton" style="display:none" value="Изменить"
                                       formaction="/ChangeActiveServiceByAdminServlet"
                                       method="post"/><input type="submit" class="deleteButton" style="display:none"
                                                             value="Удалить"
                                                             formaction="/DeleteActiveServiceByAdminServlet"
                                                             method="post"/></li>
                            <br/>
                        </div>
                    </div>
                </div>
                <%}%>
            </div>
        </div>
    </ul>
</form>
<div class="backButton">
    <a href="javascript:history.back()" class="btn btn-info btn-lg">
        <span class="glyphicon glyphicon-menu-left"></span>Назад
    </a>
</div>
</body>
</html>
