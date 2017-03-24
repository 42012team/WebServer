<%@ page import="classes.model.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="actionWithActiveService.css" rel="stylesheet">
    <link href="servicePageStyle.css" rel="stylesheet">
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
                <
                <li><a href="javascript:history.back();"><span class="glyphicon glyphicon-arrow-right">Назад</span></a>
                </li>
                <li><a href="/ShowAdminPageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <li><a href="/startPage.jsp" color="blue" class="settings">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>
<br/>
<div id="changeService"><span id="changeServiceText"><h2>Изменение услуги</h2></span></div>
    <%
    ActiveService activeService = (ActiveService) request.getAttribute("activeServiceForChanging");
    Service service = (Service) request.getAttribute("service");
    session.setAttribute("changedActiveService", activeService);
%>
<div id="activeService">
    <div class="col-md-5 text-center">
        <div class="box">
            <div class="box-content">
                <h2 class="tag-title"><span class="value"><%=service.getName()%></span></h2>
                <hr/>
                <div class="description">Описание услуги:<span
                        class="value"><%=service.getDescription()%></span>
                </div>
                <div class="description">Тип услуги: <span class="value"><%=service.getType()%></span>
                </div>
                <% if (activeService.getState().equals(ActiveServiceState.NOT_READY)) {
                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    String strDate = sdfDate.format(activeService.getDate());%>
                <div class="description">Статус услуги: <span
                        class="value"><%=activeService.getFirstStatus().toString()%></span>
                </div>
                <div class="description">Запланировано изменение статуса услуги на<span
                        class="value">
    <%=activeService.getSecondStatus().toString()%>
                                        c  <%=strDate%>
            </span></div>
                <%} else {%>
                <div class="description">Статус услуги: <span
                        class="value"><%=activeService.getSecondStatus().toString()%></span>
                </div>
                <br/>
                <br/>
                <%}%>
                <br/>
            </div>
        </div>
    </div>
</div>
<form method="post">
    <%
        if (activeService.getNextActiveServiceId() != 0) {
    %>
    <input type="submit" class="changeDateButton" formaction="/CancelChangeTariffServletByAdmin" formmethod="post"
           value="Отменить смену тарифа"/>
    <input type="submit" class="changeTariffButton" formaction="/changeNewTariffDateByAdmin.jsp" formmethod="post"
           value="Изменить дату смены тарифа"/>
    <% } else {
    %>
    <input type="submit" class="changeDateButton" formaction="/ChangeActiveServiceByAdminServlet" formmethod="post"
           value="Изменить услугу"/>
    <%if (!(activeService.getState().equals(ActiveServiceState.NOT_READY)&&(activeService.getFirstStatus().equals(ActiveServiceStatus.PLANNED)))) {%>
    <input type="submit" class="changeTariffButton" formaction="/GetTheSameTypeByCurrentServiceServlet"
           formmethod="post" value="Изменить тариф"/><%}%>
    <%}%>
</form>
</body>
</html>
