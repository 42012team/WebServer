<%@ page import="classes.model.User" %>
<%@ page import="classes.response.impl.UserResponse" %>
<%@ page import="classes.model.ActiveService" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.model.Service" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <link href="changeUserByAdminStyle.css" rel="stylesheet">
    <script src="js/bootstrap.min.js"></script>
    <link href="servicePageStyle.css" rel="stylesheet">
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

    <div class="containerBox">
        <h2>Профиль</h2>
        <%
            UserResponse user = (UserResponse) request.getAttribute("user");%>
        <input type="hidden" value="<%=user.getUserId()%>" name="userId"/>
        <ul>
            <li class="profileContainer"><label id="nameL"><span class="text">Имя:</span></label> <label
                    id="nameV"><span
                    class="text"><%=user.getName()%></span></label>
            </li>
            <li class="profileContainer"><label id="surnameL"><span class="text">Фамилия:</span></label> <label
                    id="surnameV"><span class="text"><%=user.getSurname()%></span></label></li>
            <li class="profileContainer"><label id="emailL"><span class="text">Email:</span></label> <label
                    id="emailV"><span class="text"><%=user.getEmail()%></span></label></li>
            <li class="profileContainer"><label id="phoneL"><span class="text">Телефон:</span></label> <label
                    id="phoneV"><span class="text"><%=user.getPhone()%></span></label></li>
            <li class="profileContainer"><label id="addressL"><span class="text">Адрес:</span></label> <label
                    id="addressV"><span
                    class="text"><%=user.getAddress()%></span></label></li>
            <li class="profileContainer"><label id="passwordL"><span class="text">Пароль:</span></label> <label
                    id="passwordV"><span class="text"><%=user.getPassword()%></span></label></li>
            <li class="profileContainer"><label id="privilegeL"><span class="text">Привелегия:</span></label> <label
                    id=privilegeV">
                <span class="text"><%=user.getPrivilege()%></span></label></li>

        </ul>
        <input type="submit" class="changeUserButton" formaction="/ChangeUserByAdminServlet" formmethod="post"
               value="Изменить"/>
        <input type="submit" class="deleteUserButton" formaction="/DeleteUserServlet" formmethod="post"
               value="Удалить"/>
    </div>
    </div>
    </div>
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
                                        class="value"><%=activeServiceList.get(k).getCurrentStatus().toString()%></span>
                                </div>
                                <% if (activeServiceList.get(k).getNewStatus() != null) {
                                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    String strDate = sdfDate.format(activeServiceList.get(k).getDate());%>
                                <div class="description">Запланировано изменение статуса услуги на<span class="value">
    <%= activeServiceList.get(k).getNewStatus().toString()
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


</body>
</html>