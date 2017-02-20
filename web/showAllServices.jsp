<%@ page import="classes.configuration.Initialization" %>
<%@ page import="classes.controllers.WebController" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.request.impl.TransmittedServiceParams" %>
<%@ page import="classes.response.impl.ServiceResponse" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="servicesStyle.css" rel="stylesheet">
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
                <li><a href="/ShowAllServicesServlet">Услуги</a></li>
                <li><a href="/registration.jsp" color="blue" class="settings">Зарегистрироваться</a></li>
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
            <br/>
            <p>Компания предоставляет <%=allServices.size()%> услуг:</p></h2>
        <% if (allServices.size() > 0) {%>
        <p>
        <h2 class="text-center">Услуги типа <%=allServices.get(0).getType().toString()%>
        </h2>
        </p>
        <div class="row">
            <div class="col-md-4 text-center">
                <div class="box">
                    <div class="box-content">
                        <h1 class="tag-title">
                            <%= allServices.get(0).getName()%>
                        </h1>
                        <hr/>
                        <p> Описание услуги: <%= allServices.get(0).getDescription()%>
                        </p>
                        <p> Тип услуги: <%= allServices.get(0).getType()%>
                        </p>
                        <p>Статус услуги: <%= allServices.get(0).getStatus()%>
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
        <h2 class="text-center">Услуги типа <%=allServices.get(i).getType().toString()%>
        </h2>
        </p>
        <div class="row">
            <%}%>
            <div class="col-md-4 text-center">
                <div class="box">
                    <div class="box-content">
                        <h1 class="tag-title">
                            <%= allServices.get(i).getName()%>
                        </h1>
                        <hr/>
                        <p> Описание услуги: <%= allServices.get(i).getDescription()%>
                        </p>
                        <p> Тип услуги: <%= allServices.get(i).getType()%>
                        </p>
                        <p>Статус услуги: <%= allServices.get(i).getStatus()%>
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
</form>
</body>
</html>
