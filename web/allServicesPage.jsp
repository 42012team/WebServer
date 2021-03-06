<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="servicesStyle.css" rel="stylesheet">
    <script src="allServices.js"></script>
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
                <li><a href="/WebServer_war_exploded/ShowAdminPageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <li><a href="/WebServer_war_exploded/startPage.jsp" color="blue" class="settings">Выйти</a></li>
            </ul>
        </div>
    </div>
</nav>
<%
    List<Service> allServices = (List<Service>) request.getAttribute("allServices");
%>
<form>
    <div class="container">
        <h2 class="text-center">
            <br/>
            <p>Вы можете выбрать услугу для изменения или удаления </p>
            <p>Компания предоставляет <%=allServices.size()%> услуг:</p></h2>
        <% if (allServices.size() > 0) {%>
        <p>
        <h2 class="text-center">Услуги типа <%=allServices.get(0).getType()%>
        </h2>
        </p>
        <div class="row">
            <div class="col-md-4 text-center">
                <div class="box">
                    <div class="box-content">
                        <h1 class="tag-title"><input type="radio" name="serviceId"
                                                     value="<%=allServices.get(0).getId()%>"
                                                     onclick="clickRadio(this)"/>
                            <%= allServices.get(0).getName()%>
                        </h1>
                        <hr/>
                        <p> Описание услуги: <%= allServices.get(0).getDescription()%>
                        </p>
                        <p> Тип услуги: <%= allServices.get(0).getType()%>
                        </p>
                        <p>Статус услуги: <%= allServices.get(0).getStatus()%>
                        </p>
                        <p>
                            <input type="submit" id="<%=allServices.get(0).getId()%>del" name="buttons"
                                   formaction="/WebServer_war_exploded/DeleteServiceServlet" formmethod="post"
                                   value="Удалить"
                                   style="display:none"/>
                            <input type="submit" id="<%=allServices.get(0).getId()%>ch" name="buttons"
                                   formaction="/WebServer_war_exploded/ChangeServiceServlet" formmethod="post"
                                   value="Изменить" style="display:none"/>
                        </p>
                    </div>
                </div>
            </div>
            <% if (allServices.size() > 1) {
                for (int i = 1; i < allServices.size(); i++) {
            %>
            <% if (!allServices.get(i).getType().equals(allServices.get(i - 1).getType())) {%>
        </div>
        <p>
        <h2 class="text-center">Услуги типа <%=allServices.get(i).getType()%>
        </h2>
        </p>
        <div class="row">
            <%}%>
            <div class="col-md-4 text-center">
                <div class="box">
                    <div class="box-content">
                        <h1 class="tag-title"><input type="radio" name="serviceId"
                                                     value="<%=allServices.get(i).getId()%>"
                                                     onclick="clickRadio(this)"/>
                            <%= allServices.get(i).getName()%>
                        </h1>
                        <hr/>
                        <p> Описание услуги: <%= allServices.get(i).getDescription()%>
                        </p>
                        <p> Тип услуги: <%= allServices.get(i).getType()%>
                        </p>
                        <p>Статус услуги: <%= allServices.get(i).getStatus()%>
                        </p>
                        <p>
                            <input type="submit" id="<%=allServices.get(i).getId()%>del" name="buttons"
                                   formaction="/WebServer_war_exploded/DeleteServiceServlet" formmethod="post"
                                   value="Удалить"
                                   style="display:none"/>
                            <input type="submit" id="<%=allServices.get(i).getId()%>ch" name="buttons"
                                   formaction="/WebServer_war_exploded/ChangeServiceServlet" formmethod="post"
                                   value="Изменить" style="display:none"/>
                        </p>
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
    <div><h4 class="text-center">
        <input type="submit" formaction="/WebServer_war_exploded/addServicePage.jsp" formmethod="post"
               value="Добавить новую услугу"/>
    </h4>
    </div>
</form>
</body>
</html>

