<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.User" %>
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
                <li><a href="javascript:history.back();"><span class="glyphicon glyphicon-arrow-right">Назад</span></a>
                </li>
                <li><a href="/ShowActiveServicesServlet" color="blue" class="settings">Мои услуги</a></li>
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
<form action="/AddActiveServiceServlet"
      method="post" <%if (((User) session.getAttribute("user")).getPrivilege().equals("admin")) {%>
      onsubmit="javascript:
var d=new Date();
var dateInput=document.getElementsByName('activationDate'+$('input[name=serviceId]:checked').val().toString())[0].value;
if(Date.parse(new Date(d.getTime()-d.getTimezoneOffset()*60*1000))>Date.parse(dateInput)){
    return confirm('Введена прошедшая дата! Услуга сразу станет подключенной. Вы уверены?');
}"
        <%}%>>
    <div id="usersActiveServices"><span id="connectService"><h2>Подключить услугу</h2></span></div>
    <ul>
        <%
            List<Service> allServices = (List<Service>) request.getAttribute("allowedToConnectServices");
        %>
        <div class="container">
            <% if (allServices.size() > 0) {%>
            <p>
            <h2 class="text-center">Услуги типа <%=allServices.get(0).getType().toString()%>
            </h2>
            </p>
            <div class="row">
                <div class="col-md-4 text-center">
                    <div class="box">
                        <div class="box-content">
                            <h1 class="tag-title">
                                <%= allServices.get(0).getName()%>
                            </h1>
                            <li class="li1">
                                <input type="radio" class="radio" name="serviceId" onclick="click1(this)"
                                       id="<%=allServices.get(0).getId()%>" value="<%=allServices.get(0).getId()%>">
                                <div class="description">Описание услуги:<span
                                        class="value"><%=allServices.get(0).getDescription()%></span></div>
                                <div class="description">Тип услуги: <span
                                        class="value"><%=allServices.get(0).getType()%></span></div>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=allServices.get(0).getStatus().toString()%></span></div>
                                <input type="datetime-local" class="dateField" style="display:none"
                                       name="activationDate<%=allServices.get(0).getId()%>" id="date-input0"><input
                                    type="submit" class="addButton"
                                    style="display:none"
                                    value="Добавить"/></li>
                        </div>
                    </div>
                </div>
                <% if (allServices.size() > 1) {
                    for (int i = 1; i < allServices.size(); i++) {
                %>
                <% if (!allServices.get(i).getType().equals(allServices.get(i - 1).getType())) {%>
            </div>
            <p>
            <h2 class="text-center">Услуги типа <%=allServices.get(i).getType().toString()%>
            </h2>
            </p>
            <div class="row">
                <%}%>
                <div class="col-md-4 text-center">
                    <div class="box">
                        <div class="box-content">
                            <h1 class="tag-title">
                                <%= allServices.get(i).getName()%>
                            </h1>
                            <li class="li1">
                                <input type="radio" class="radio" name="serviceId" onclick="click1(this)"
                                       id="<%=allServices.get(i).getId()%>" value="<%=allServices.get(i).getId()%>">
                                <div class="description">Описание услуги:<span
                                        class="value"><%=allServices.get(i).getDescription()%></span></div>
                                <div class="description">Тип услуги: <span
                                        class="value"><%=allServices.get(i).getType()%></span></div>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=allServices.get(i).getStatus().toString()%></span></div>
                                <input type="datetime-local" class="dateField" style="display:none"
                                       name="activationDate<%=allServices.get(i).getId()%>"
                                       id="date-input<%=i%>>"><input type="submit" class="addButton"
                                                                     style="display:none"
                                                                     value="Добавить"/></li>
                        </div>
                    </div>
                </div>
                <% if (i == allServices.size() - 1) {%>
            </div>
            <%}%>
            <%
                        }
                    }
                }
            %>
        </div>
    </ul>
</form>
</body>
</html>
