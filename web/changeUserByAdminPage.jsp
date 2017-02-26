<%@ page import="classes.model.User" %>
<%@ page import="classes.response.impl.UserResponse" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="servicePageStyle.css" rel="stylesheet">
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
    <div class="col-md-6 text-center">
        <div class="box">
            <div class="box-content">
                <h2>Редактирование</h2>
                <p><input type="submit" name="saveButton" id="deleteButton" value="Удалить"
                          formaction="/DeleteUserServlet" formmethod="post"></p>
                <%
                    UserResponse user = (UserResponse) request.getAttribute("user");%>
                <p>Пароль: <input type="text" name="password" id="password" value="<%=user.getPassword()%>"></p>
                <p>Имя: <input type="text" name="name" id="name" value="<%=user.getName()%>"/></p>
                <p>Фамилия: <input type="text" name="surname" id="surname" value="<%=user.getSurname()%>"/></p>
                <p>Email: <input type="text" name="email" id="email" value="<%=user.getEmail()%>"/></p>
                <p>Телефон<input type="text" name="phone" id="phone" value="<%=user.getPhone()%>"/></p>
                <p>Адрес: <input type="text" name="address" id="address" value="<%=user.getAddress()%>"/></p>
                <p>Привилегированность: <input type="radio" name="privilege" value="admin"<%
    if(user.getPrivilege().equals("admin")){
        %> checked<%}%>>ADMIN <input type="radio" name="privilege" value="user"<%
        if(user.getPrivilege().equals("user")){
    %> checked<%
        }
    %>>USER</p>
                <input type="hidden" value="<%=user.getUserId()%>" name="userId"/>
                <input type="hidden" value="<%=user.getVersion()%>" name="version"/>
                <input type="hidden" value="<%=user.getLogin()%>" name="login"/>
                <p><input type="submit" name="saveButton" id="saveButton" value="Сохранить"
                          formaction="/ChangeUserByAdminRespServlet" formmethod="post"></p>
            </div>
        </div>
    </div>
</form>
</body>
</html>