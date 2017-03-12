<%@ page import="classes.model.ActiveService" %>
<%@ page import="classes.model.ActiveServiceStatus" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                List<ActiveService> activeServicesList = (List<ActiveService>) request.getAttribute("activeServicesList");
                List<Service> allServices = (List<Service>) request.getAttribute("activeServicesDescriptions");
                if (allServices.size() > 0) {%>
            <p>
            <h2 class="text-center">Услуги типа <%=allServices.get(0).getType().toString()%>
            </h2>
            </p>
            <div class="row">
                <div class="col-md-4 text-center">
                    <div class="box">
                        <div class="box-content">
                            <h2 class="tag-title"><span class="value"><%=allServices.get(0).getName()%></span></h2>
                            <hr/>
                            <li>
                                <%
                                    int num = 0;
                                    int serviceId = allServices.get(0).getId();
                                    for (int i = 0; i < activeServicesList.size(); i++) {
                                        if (activeServicesList.get(i).getServiceId() == serviceId) {
                                            num = i;
                                            break;
                                        }
                                    }%>
                                <input type="radio" class="radio" name="chooseActiveService" onclick="click1(this)"
                                       id="<%=activeServicesList.get(num).getId()%>"
                                       value="<%=activeServicesList.get(num).getId()%>">
                                <div class="description">Описание услуги:<span
                                        class="value"><%=allServices.get(0).getDescription()%></span>
                                </div>
                                <div class="description">Тип услуги: <span
                                        class="value"><%=allServices.get(0).getType()%></span></div>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=activeServicesList.get(num).getCurrentStatus().toString()%></span>
                                </div>
                                <% if (activeServicesList.get(num).getNewStatus() != null) {
                                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    String strDate = sdfDate.format(activeServicesList.get(num).getDate());%>
                                <div class="description">Запланировано изменение статуса услуги на<span
                                        class="value">
    <%= activeServicesList.get(num).getNewStatus().toString()
    %> c  <%=strDate%>
            </span></div>
                                <%} else {%>
                                <br/>
                                <br/>
                                <%
                                    }
                                    if ((activeServicesList.get(num).getNewStatus() == null) || ((activeServicesList.get(num).getNewStatus() != null) && (!activeServicesList.get(num).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED))) ||
                                            ((activeServicesList.get(num).getNewStatus() != null) && (activeServicesList.get(num).getDate().compareTo(new Date()) <= 0)) && (activeServicesList.get(num).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED))) {
                                %>
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
                            <li>
                                <%
                                    num = 0;
                                    String type = allServices.get(i).getType();
                                    boolean isSecond = false;
                                    if (allServices.get(i).getType().equals(allServices.get(i - 1).getType()))
                                        isSecond = true;
                                    boolean bool = isSecond;
                                    for (int j = 0; j < activeServicesList.size(); j++) {
                                        boolean hasFind = false;
                                        for (int k = 0; k < allServices.size(); k++) {
                                            if ((activeServicesList.get(j).getServiceId() == allServices.get(k).getId()) &&
                                                    (allServices.get(k).getType().equals(type))) {
                                                if (!isSecond) {
                                                    num = j;
                                                    serviceId=k;
                                                    hasFind = true;
                                                    break;
                                                } else {
                                                    isSecond = false;
                                                    break;
                                                }
                                            }
                                        }
                                        if (hasFind)
                                            break;
                                    }
                                    %>
                                <h2 class="tag-title"><span class="value"><%=allServices.get(serviceId).getName()%></span></h2>
                                <hr/>
                                <%
                                    if (!bool) {
                                %>
                                <input type="radio" class="radio" name="chooseActiveService" onclick="click1(this)"
                                       id="<%=activeServicesList.get(num).getId()%>"
                                       value="<%=activeServicesList.get(num).getId()%>">
                                <%}%>
                                <div class="description">Описание услуги:<span
                                        class="value"><%=allServices.get(serviceId).getDescription()%></span>
                                </div>
                                <div class="description">Тип услуги: <span
                                        class="value"><%=allServices.get(serviceId).getType()%></span></div>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=activeServicesList.get(num).getCurrentStatus().toString()%></span>
                                </div>
                                <% if (activeServicesList.get(num).getNewStatus() != null) {
                                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    String strDate = sdfDate.format(activeServicesList.get(num).getDate());%>
                                <div class="description">Запланировано изменение статуса услуги на<span
                                        class="value">
    <%= activeServicesList.get(num).getNewStatus().toString()
    %> c  <%=strDate%>
            </span></div>
                                <%} else {%>
                                <br/>
                                <br/>
                                <%
                                    }
                                    if ((activeServicesList.get(num).getNewStatus() == null) || ((activeServicesList.get(num).getNewStatus() != null) && (!activeServicesList.get(num).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED))) ||
                                            ((activeServicesList.get(num).getNewStatus() != null) && (activeServicesList.get(num).getDate().compareTo(new Date()) <= 0)) && (activeServicesList.get(num).getNewStatus().equals(ActiveServiceStatus.DISCONNECTED))) {
                                %>
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
