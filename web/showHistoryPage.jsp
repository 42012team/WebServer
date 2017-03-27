<%@ page import="classes.model.ActiveService" %>
<%@ page import="classes.model.ActiveServiceState" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.User" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
    <link href="showActiveServicesStyle.css">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="AllUserss.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="allUsers.js"></script>
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
                <li><a href="/ShowAllServicesServlet">Все услуги</a></li>
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
<%
    List<ActiveService> activeServicesList = (List<ActiveService>) request.getAttribute("activeServicesList");
    List<Service> allServices = (List<Service>) request.getAttribute("activeServicesDescriptions");%>
<br/><br/><br/>
<h2 style="margin-left: 37%;">История услуг типа <%=allServices.get(0).getType()%></h2>
<br/>
<form>
    <table id="example" class="display" cellspacing="0">
        <thead>
        <tr>
            <th>Услуга</th>
            <th>Статус</th>
            <th>Изменение</th>
            <th>Дата</th>
        </tr>
        </thead>
        <tfoot>
        <tr>
            <th>Услуга</th>
            <th>Статус</th>
            <th>Изменение</th>
            <th>Дата</th>
        </tr>
        </tfoot>
        <tbody>
        <%
            for (int k = 0; k < allServices.size(); k++) {
                Service s = allServices.get(k);
        %>
        <tr>
            <td><a href="/ShowServiceDetailsServlet?serviceId=<%=s.getId()%>"><%=s.getName()%></a>
            </td>
            <td><%=activeServicesList.get(k).getFirstStatus().toString()%>
            </td>
            <td><% if (activeServicesList.get(k).getState().equals(ActiveServiceState.NOT_READY)) {
            %>Запланировано изменение статуса услуги на <%= activeServicesList.get(k).getSecondStatus().toString()%>                <%
                } else {%>
                Статус услуги был изменен на <%= activeServicesList.get(k).getSecondStatus().toString()%> c
                <%
                    }
                %>
            </td>
            <td><%
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String strDate = sdfDate.format(activeServicesList.get(k).getDate());
            %><%=strDate%>
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
