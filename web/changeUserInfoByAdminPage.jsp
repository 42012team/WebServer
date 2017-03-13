<%@ page import="classes.model.ActiveService" %>
<%@ page import="classes.model.ActiveServiceStatus" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.User" %>
<%@ page import="classes.response.impl.UserResponse" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <link href="changeUserInfoByAdminStyle.css" rel="stylesheet">
    <script src="js/bootstrap.min.js"></script>
    <link href="servicePageStyle.css" rel="stylesheet">
    <link href="showActiveServicesStyle.css" rel="stylesheet">
    <link href="1.css" rel="stylesheet">
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

<form method="post">
    <div class="container" id="containerProfile">
        <div class="row">
            <%
                UserResponse user = (UserResponse) request.getAttribute("user");%>
            <input type="hidden" value="<%=user.getUserId()%>" name="userId"/>
            <%

            %>
            <div class="col-md-4 text-center">
                <div class="box">
                    <div class="box-content">
                        <h2 class="tag-title"><span class="value">Профиль</span></h2>
                        <hr/>
                        <p>Логин:<%=user.getLogin()%>
                        </p>
                        <p>Имя:<%=user.getName()%>
                        </p>
                        <p>Фамилия:<%=user.getSurname()%>
                        </p>
                        <p>Email:<%=user.getEmail()%>
                        </p>
                        <p>Телефон:<%=user.getPhone()%>
                        </p>
                        <p>Адрес:<%=user.getAddress()%>
                        </p>
                        <p>Пароль:<%=user.getPassword()%>
                        </p>
                        <p>Привелегия:<%=user.getPrivilege()%>
                        </p>
                        <input type="submit" class="changeUserButton" formaction="/ChangeUserByAdminServlet"
                               formmethod="post"
                               value="Изменить"/>
                        <%
                            if ((user.getUserId() != ((User) session.getAttribute("user")).getId())
                                    && (!user.getLogin().equals("admin"))) {
                        %>
                        <input type="submit" class="deleteUserButton" formaction="/DeleteUserServlet" formmethod="post"
                               value="Удалить"/>
                        <% } %>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <%session.setAttribute("userForChange", user.getUserId());%>
    <input type="submit" name="" class="addActiveServiceButton" value="Подключить услугу"
           formaction="/ShowAllowedToConnectServicesByAdminServlet" formmethod="post">
    <form method="post">
        <div id="usersActiveServices"><span id="connectService"><h2>Подключенные услуги</h2></span></div>
        <ul>
            <div class="container">
                <%
                    List<ActiveService> activeServicesList = (List<ActiveService>) request.getAttribute("activeServicesList");
                    List<Service> allServices = (List<Service>) request.getAttribute("activeServicesDescriptions");
                    if (allServices.size() > 0) {%>
                <p>
                <h2 class="text-center"><a href="/ShowActiveServicesHistoryServlet?user_id=<%=user.getUserId()%>&service_id=<%=allServices.get(0).getId()%>">Услуги типа <%=allServices.get(0).getType()%></a>
                </h2>
                </p>
                <div class="row">
                    <div class="col-md-4 text-center">
                        <div class="box">
                            <div class="box-content">
                                <li>
                                    <%
                                        int num = 0;
                                        int serviceId = 0;
                                        String type = allServices.get(0).getType();
                                        boolean isSecond = false;
                                        boolean bool = isSecond;
                                        for (int j = 0; j < activeServicesList.size(); j++) {
                                            boolean hasFind = false;
                                            for (int k = 0; k < allServices.size(); k++) {
                                                if ((activeServicesList.get(j).getServiceId() == allServices.get(k).getId()) &&
                                                        (allServices.get(k).getType().equals(type))) {
                                                    if (!isSecond) {
                                                        num = j;
                                                        serviceId = k;
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
                                        }%>
                                    <h2 class="tag-title"><span
                                            class="value"><%=allServices.get(serviceId).getName()%></span></h2>
                                    <hr/>
                                    <input type="radio" class="radio" name="chooseActiveService" onclick="click1(this)"
                                           id="<%=activeServicesList.get(num).getId()%>"
                                           value="<%=activeServicesList.get(num).getId()%>">
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
                                           formaction="/ActionWithActiveServiceByAdminServlet"
                                           method="post"/><input type="submit" class="deleteButton" style="display:none"
                                                                 value="Удалить"
                                                                 formaction="/DeleteActiveServiceByAdminServlet"
                                                                 method="post"/></li>
                                <%} else {%>
                                <input type="submit" class="changeButton" style="display:none" value="Изменить"
                                       formaction="/ActionWithActiveServiceByAdminServlet"
                                       method="post"/><input type="submit" class="deleteButton" style="display:none"
                                                             value="Удалить"
                                                             formaction="/DeleteActiveServiceWithSameTypeByAdminServlet"
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
                <h2 class="text-center"><a href="/ShowActiveServicesHistoryServlet?user_id=<%=user.getUserId()%>&service_id=<%=allServices.get(i).getId()%>">Услуги типа <%=allServices.get(i).getType()%></a>
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
                                        type = allServices.get(i).getType();
                                        isSecond = false;
                                        if (allServices.get(i).getType().equals(allServices.get(i - 1).getType()))
                                            isSecond = true;
                                        bool = isSecond;
                                        for (int j = 0; j < activeServicesList.size(); j++) {
                                            boolean hasFind = false;
                                            for (int k = 0; k < allServices.size(); k++) {
                                                if ((activeServicesList.get(j).getServiceId() == allServices.get(k).getId()) &&
                                                        (allServices.get(k).getType().equals(type))) {
                                                    if (!isSecond) {
                                                        num = j;
                                                        serviceId = k;
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
                                    <h2 class="tag-title"><span
                                            class="value"><%=allServices.get(serviceId).getName()%></span></h2>
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
                                           formaction="/ActionWithActiveServiceByAdminServlet"
                                           method="post"/><input type="submit" class="deleteButton" style="display:none"
                                                                 value="Удалить"
                                                                 formaction="/DeleteActiveServiceByAdminServlet"
                                                                 method="post"/></li>
                                <%} else {%>
                                <input type="submit" class="changeButton" style="display:none" value="Изменить"
                                       formaction="/ActionWithActiveServiceByAdminServlet"
                                       method="post"/><input type="submit" class="deleteButton" style="display:none"
                                                             value="Удалить"
                                                             formaction="/DeleteActiveServiceWithSameTypeByAdminServlet"
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
</form>
</body>
</html>