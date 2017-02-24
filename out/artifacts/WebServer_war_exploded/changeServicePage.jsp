<%@ page import="classes.configuration.Initialization" %>
<%@ page import="classes.model.ServiceStatus" %>
<%@ page import="classes.model.Service" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
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
                <li><a href="#about">О Нас</a></li>
            </ul>
        </div>
    </div>
</nav>
<form>
    <div class="col-md-6 text-center">
        <div class="box">
            <div class="box-content">
                <h2>Изменение услуги</h2>
                <p>Название услуги:<input type="text" name="name" value="<%=request.getAttribute("name")%>"></p>
                <p>Описание услуги:<input type="text" name="description"
                                          value="<%=request.getAttribute("description")%>"></p>
                <p>Статус услуги:
                <p><input type="radio" name="status" value="ALLOWED"<%
    if(request.getAttribute("status").equals(ServiceStatus.ALLOWED)){
        %> checked<%
    }
    %>>ALLOWED</p>
                <p><input type="radio" name="status" value="DEPRECATED"<%
        if(request.getAttribute("status").equals(ServiceStatus.DEPRECATED)){
    %> checked<%
        }
    %>>DEPRECATED</p></p>
                <input type="hidden" name="serviceId" value="<%=request.getAttribute("serviceId")%>"/>
                <input type="hidden" name="version" value="<%=request.getAttribute("version")%>"/>
                <input type="submit" formaction="/ChangeServiceRespServlet" formmethod="post" value="Изменить услугу"/>
            </div>
        </div>
    </div>
</form>
</body>
</html>
