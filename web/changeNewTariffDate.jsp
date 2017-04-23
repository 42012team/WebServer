<%@ page import="classes.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="changeActiveServiceStyle.css" rel="stylesheet">
    <link href="newStyleForChangeActiveServicePage.css" rel="stylesheet">
    <script src="1.js"></script>
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
                <li><a href="/WebServer_war_exploded/ShowAllServicesServlet">Все услуги</a></li>
                <li><a href="/WebServer_war_exploded/ShowProfilePageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <li><a href="/WebServer_war_exploded/startPage.jsp">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>
<form method="post" <%if (((User) session.getAttribute("user")).getPrivilege().equals("admin")) {%>
      onsubmit="javascript:
var d=new Date();
if(Date.parse(new Date(d.getTime()-d.getTimezoneOffset()*60*1000))>Date.parse($('#date').val())){
    return confirm('Введена прошедшая дата! Изменения сразу вступят в силу. Вы уверены?');
}"><%
} else {
%>

    <%
        }
        int id = Integer.parseInt(request.getParameter("chooseActiveService"));
        session.setAttribute("changedActiveServiceId", id);

    %>
    <div class="container">
        <div class="row">
            <div class="col-md-6 text-center">
                <div class="box">
                    <div class="box-content">
                        <h2 class="tag-title"> Изменение даты смены тарифа:</h2>
                        <hr/>
                        <p>Введите новую дату смены тарифа в формате:</p>
                        <p><strong>ДД.ММ.ГГГГ ЧЧ:ММ</strong></p>
                        <input type="datetime-local" name="date" id="date" class="calendar" required/>
                        <input type="submit" class="changeDateButton"
                               formaction="/WebServer_war_exploded/ChangeNewTariffDateServlet"
                               formmethod="post"
                               value="Изменить дату"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
