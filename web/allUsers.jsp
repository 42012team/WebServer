<%@ page import="classes.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="AllUserss.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="allUsers.js"></script>
    <script src="userDetails.js"></script>
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
                <li><a href="/addUserPage.jsp" color="blue" class="settings">Добавить пользователя</a></li>
                <li><a href="/ShowAdminPageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <li><a href="/startPage.jsp" color="blue" class="settings">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>
<br/><br/><br/>
<form method="post">
    <table id="example" class="display" cellspacing="0">
        <thead>
        <tr>
            <th>Выбрать</th>
            <th>Пользователь</th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <th width="3%">Выбрать</th>
            <th>Name</th>
        </tr>
        </tfoot>
        <tbody>
        <%
            List<User> allUsers = (List<User>) request.getAttribute("allUsers");
            int j = 1;
            for (User user : allUsers) {
        %>
        <tr>
            <td width="3%"><input type="checkbox" name="chooseUser" value="<%=user.getId()%>" id="<%=user.getId()%>"
                       onclick="myFunc(this)"/>
            </td>
            <td>
                <a href="/ChangeUserInfoByAdminServlet?user_id=<%=user.getId()%>"><%=user.getSurname()%> <%=user.getName()%>
                </a>
            </td>
        </tr>

        <%
            }
        %>
        </tbody>
    </table>
    <input type="submit" name="exportButton" value="Export" formaction="ExportServlet" formmethod="post"/>
    <input type="submit" name="exportButton" formaction="import.jsp"  formmethod="post" value="Import"/>
</form>
</body>
</html>