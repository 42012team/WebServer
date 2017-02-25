<%@ page import="classes.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="allUsers.css" rel="stylesheet">
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
                <li><a href="#about">О Нас</a></li>
            </ul>
        </div>
    </div>
</nav>
<br/><br/><br/>
<form method="post">
<table id="example" class="display" cellspacing="0" width="100%">
    <thead>
    <tr>
        <th>Выбрать</th>
        <th>ID</th>
        <th>Privilege</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Login</th>
        <th>Password</th>
        <th>Address</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Действие</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <th>Выбрать</th>
        <th>ID</th>
        <th>Privilege</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Login</th>
        <th>Password</th>
        <th>Address</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Действие</th>
    </tr>
    </tfoot>
    <tbody>
    <%
        List<User> allUsers = (List<User>) request.getAttribute("allUsers");
        int j = 1;
        for (User user : allUsers) {
    %>
    <tr>
        <td><input type="radio" name="chooseUser" value="<%=user.getId()%>" id="<%=user.getId()%>" onclick="myFunc(this)"/>
        </td>
        <td><%=user.getId()%>
        </td>
        <td><%=user.getPrivilege()%>
        </td>
        <td><%=user.getName()%>
        </td>
        <td><%=user.getSurname()%>
        </td>
        <td><%=user.getLogin()%>
        </td>
        <td><%=user.getPassword()%>
        </td>
        <td><%=user.getAddress()%>
        </td>
        <td><%=user.getEmail()%>
        </td>
        <td><%=user.getPhone()%>
        </td>
        <td>
            <input type="submit" class="buttons" value="Услуги" id="<%=user.getId()%>services" disabled/>
            <input type="submit" class="buttons" value="Профиль" id="<%=user.getId()%>profile" disabled
                   formaction="/ChangeUserByAdminServlet" formmethod="post"/>
        </td>
    </tr>

    <%
        }
    %>
    </tbody>
</table>
</form>
</body>
</html>
