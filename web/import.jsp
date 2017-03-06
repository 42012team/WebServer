<%@ page import="classes.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="allUsers.js"></script>
    <script src="userDetails.js"></script>
    <link href="ImportStyle.css" rel="stylesheet">
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
<br><br>
<!--<form method="post" action="UploadServlet"
      enctype="multipart/form-data">
    <input type="file" class="fille" name="file" size="60" /><br />
    <br /> <input type="submit" value="Upload" />
    </form>-->
<form method="post" action="ImportServlet"
      enctype="multipart/form-data" class="importForm">
    <br />
    <div class= "pathContainer"> <input type="file" class="file" name="file"  />
    <input type="submit" name="" class="pathButton" value="OK"/>
     </div>
</form>
</body>
</html>
