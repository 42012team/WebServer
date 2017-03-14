<%@ page import="classes.model.ActiveService" %>
<%@ page import="classes.model.ActiveServiceState" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
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
                <li><a href="/ShowAllowedToConnectServicesServlet" color="blue" class="settings">Подключить услугу</a>
                </li>
                <%
                    switch (((User) session.getAttribute("user")).getPrivilege()) {
                        case "user":
                %>
                <li><a href="/ShowProfilePageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <%
                        break;
                    case "admin":
                %>
                <li><a href="/ShowAdminPageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <%
                            break;
                    }
                %>
                <li><a href="/startPage.jsp">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>
<form method="post">
    <div id="usersActiveServices"><span id="connectService"><h2>Подключенные услуги</h2></span></div>
    <ul>
        <div class="container">
                <%
                    List<ActiveService> activeServiceList = (List<ActiveService>) request.getAttribute("activeServicesList");
                    List<Integer> activeServicesWithNotNullNextId = new ArrayList<Integer>();
                    for (int i = 0; i < activeServiceList.size(); i++) {
                        if (activeServiceList.get(i).getNextActiveServiceId() != 0) {
                            activeServicesWithNotNullNextId.add(activeServiceList.get(i).getNextActiveServiceId());
                        }
                    }
                    List<Service> servicesList = (List<Service>) request.getAttribute("activeServicesDescriptions");
                    if(servicesList.size()>0){
                        %>
            <h2 class="text-center">Услуги типа <%=servicesList.get(0).getType().toString()%>
            </h2>
            <div class="row">
                <%
                    }
                    for (int k = 0; k < servicesList.size(); k++) {
                        boolean isExist = false;
                        for (int j = 0; j < activeServicesWithNotNullNextId.size(); j++) {
                            if (activeServiceList.get(k).getId() == activeServicesWithNotNullNextId.get(j)) {
                                isExist = true;
                                break;
                            }
                        }
                        Service s = servicesList.get(k);
                        if((k>0)&&(!servicesList.get(k).getType().equals(servicesList.get(k-1).getType()))){
                %>
            </div>
            <h2 class="text-center">Услуги типа <%=servicesList.get(k).getType().toString()%>
            </h2>
            <div class="row">
                <%
                    }
                %>
                <div class="col-md-4 text-center">
                    <div class="box">
                        <div class="box-content">
                            <h2 class="tag-title"><span class="value"><%=s.getName()%></span></h2>
                            <hr/>
                            <li>
                                <%if (!isExist) {%>
                                <input type="radio" class="radio" name="chooseActiveService" onclick="click1(this)"
                                       id="<%=activeServiceList.get(k).getId()%>"
                                       value="<%=activeServiceList.get(k).getId()%>"><%}%>
                                <div class="description">Описание услуги:<span
                                        class="value"><%=s.getDescription()%></span>
                                </div>
                                <div class="description">Тип услуги: <span class="value"><%=s.getType()%></span></div>
                                <% if (activeServiceList.get(k).getState().equals(ActiveServiceState.NOT_READY)) {
                                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    String strDate = sdfDate.format(activeServiceList.get(k).getDate());%>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=activeServiceList.get(k).getFirstStatus().toString()%></span>
                                </div>
                                <div class="description">Запланировано изменение статуса услуги на<span class="value">
    <%= activeServiceList.get(k).getSecondStatus().toString()
    %> c  <%=strDate%>
            </span></div>
                                <%} else {%>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=activeServiceList.get(k).getSecondStatus().toString()%></span>
                                </div>
                                <br/>
                                <br/>
                                <%}%>
                                <% if (isExist) {%>
                                <input type="submit" class="changeButton" style="display:none" value="Изменить"
                                       formaction="/ActionWithActiveServiceServlet"
                                       method="post"/><input type="submit" class="deleteButton" style="display:none"
                                                             value="Удалить"
                                                             formaction="/DeleteActiveServiceServlet"
                                                             method="post"/></li>
                            <%} else {%>
                            <input type="submit" class="changeButton" style="display:none" value="Изменить"
                                   formaction="/ActionWithActiveServiceServlet"
                                   method="post"/><input type="submit" class="deleteButton" style="display:none"
                                                         value="Удалить"
                                                         formaction="/DeleteActiveServiceByTheSameTypeServlet"
                                                         method="post"/></li><%
                            }
                        %>
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
