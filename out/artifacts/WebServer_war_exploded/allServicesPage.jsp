<%@ page import="classes.configuration.Initialization" %>
<%@ page import="classes.controllers.WebController" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.request.impl.TransmittedServiceParams" %>
<%@ page import="classes.response.impl.ServiceResponse" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    List<Service> allServices = (List<Service>)request.getAttribute("allServices");
%>
<form>
    <p>Вы можете выбрать услугу для изменения или удаления: </p>
    <p>Компания предоставляет <%=allServices.size()%> услуг:</p>
    <%
        for (int i = 0; i < allServices.size(); i++) {
    %>
    <p><input type="radio" name="serviceId" value="<%=allServices.get(i).getId()%>"/> Услуга номер <%= i + 1%></p>
    <p> Название услуги: <%= allServices.get(i).getName()%></p>
    <p> Описание услуги: <%= allServices.get(i).getDescription()%></p>
    <p> Тип услуги: <%= allServices.get(i).getType()%></p>
    <p>Статус услуги: <%= allServices.get(i).getStatus()%></p>
    <%
        }
    %>
    <input type="submit" formaction="DeleteServiceServlet" formmethod="post" value="Удалить выбранную услугу"/>
    <input type="submit" formaction="ChangeServiceServlet" formmethod="post" value="Изменить выбранную услугу"/>
    <input type="submit" formaction="addServicePage.jsp" formmethod="post" value="Добавить новую услугу"/>
</form>
</body>
</html>
