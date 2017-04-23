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
                <li><a href="/WebServer_war_exploded/ShowAllServicesServlet">Все услуги</a></li>
                </li>
                <%
                    if (((User) session.getAttribute("user")).getPrivilege().equals("user")) {
                %>
                <li><a href="/WebServer_war_exploded/ShowProfilePageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <%
                } else if (((User) session.getAttribute("user")).getPrivilege().equals("admin")) {
                %>
                <li><a href="/WebServer_war_exploded/ShowAdminPageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <%

                    }
                %>
                <li><a href="/WebServer_war_exploded/startPage.jsp">Выйти</a></li>
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
            ActiveService activeService=null;
            Service service=null;
            for (int k = 0; k < allServices.size(); k++) {
                activeService=activeServicesList.get(k);
                for (Service s:allServices){
                    if (s.getId()==activeService.getServiceId()){
                        service=s;
                    }
                }
        %>
        <tr>
            <td>
                <a href="/WebServer_war_exploded/ShowServiceDetailsServlet?serviceId=<%=service.getId()%>"><%=service.getName()%>
                </a>
            </td>
            <td><%=activeService.getFirstStatus().toString()%>
            </td>
            <td><% if (activeService.getState().equals(ActiveServiceState.NOT_READY)) {
            %>Запланировано изменение статуса услуги на <%= activeService.getSecondStatus().toString()%>                <%
                } else {%>
                Статус услуги был изменен на <%= activeService.getSecondStatus().toString()%>
                <%
                    }
                %>
            </td>
            <td><%
                SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                String strDate = sdfDate.format(activeService.getDate());
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
