<%@ page import="classes.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="changeProfileStyle.css" rel="stylesheet">
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
                <li><a href="/WebServer_war_exploded/ShowAllServicesServlet">Все услуги</a></li>
                <li><a href="/WebServer_war_exploded/ShowActiveServicesServlet" color="blue" class="settings">Мои
                    услуги</a></li>
                <%
                    if (((User) session.getAttribute("user")).getPrivilege().equals("user")) {
                %>
                <li><a href="/WebServer_war_exploded/ShowProfilePageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <%
                } else if (((User) session.getAttribute("user")).getPrivilege().equals("user")) {
                %>
                <li><a href="/WebServer_war_exploded/ShowAdminPageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <%
                    }
                %>
                <li><a href="javascript:history.back();"><span class="glyphicon glyphicon-arrow-right">Назад</span></a>
                </li>
                <li><a href="/WebServer_war_exploded/startPage.jsp">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>

<form class="changeForm" action="/WebServer_war_exploded/ChangeUserServlet" method="post">
    <div class="regHeader">
        <h2 class="headerText">Редактирование</h2>
    </div>
    <ul>
        <%
            User user = (User) session.getAttribute("user");%>
        <li class="changeContainer"><label id="passwordL" for="password"><span class="text">Пароль</span></label><input
                type="text" name="password" id="password" value="<%=user.getPassword()%>" required></li>
        <li class="changeContainer"><label id="nameL" for="name"><span class="text">Имя</span></label><input
                type="text" name="name" id="name" value="<%=user.getName()%>" required/></li>
        <li class="changeContainer"><label id="surnameL" for="surname"><span class="text">Фамилия</span></label><input
                type="text" name="surname" id="surname" value="<%=user.getSurname()%>" required/></li>
        <li class="changeContainer"><label id="emailL" for="email"><span class="text">Email</span></label><input
                type="text" name="email" id="email" value="<%=user.getEmail()%>"/></li>
        <li class="changeContainer"><label id="phoneL" for="phone"><span class="text">Телефон</span></label><input
                type="text" name="phone" id="phone" value="<%=user.getPhone()%>"/></li>
        <li class="changeContainer"><label id="addressL" for="address"><span class="text">Адрес</span></label><input
                type="text" name="address" id="address" value="<%=user.getAddress()%>" required/></li>
    </ul>

    <input type="submit" name="saveButton" id="saveButton" value="Сохранить">
</form>

</body>
</html>
