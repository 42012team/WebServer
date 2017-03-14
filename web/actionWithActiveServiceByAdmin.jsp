<%@ page import="classes.model.ActiveService" %>
<%@ page import="classes.model.ActiveServiceStatus" %>
<%@ page import="classes.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="showActiveServicesStyle.css" rel="stylesheet">
    <link href="ChooseChangeActiveServiceStyle.css" rel="stylesheet">
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
<form method="post" class="changeForm">
    <%
        ActiveService activeService = (ActiveService) request.getAttribute("activeServiceForChanging");
        session.setAttribute("changedActiveServiceId", activeService.getId());
        if ((activeService.getSecondStatus() != null) && (activeService.getSecondStatus().equals(ActiveServiceStatus.DISCONNECTED))) {
    %>
    <input type="submit" class="changeDateButton1" formaction="/ChangeActiveServiceByAdminServlet" formmethod="post"
           value="Изменить услугу"/>

    <% } else {
    %>
    <input type="submit" class="changeTariffButton" formaction="/GetTheSameTypeByCurrentServiceByAdminServlet"
           formmethod="post" value="Изменить тариф"/>
    <input type="submit" class="changeDateButton" formaction="/ChangeActiveServiceByAdminServlet" formmethod="post"
           value="Изменить услугу"/>
    <%}%>
</form>
</body>
</html>
