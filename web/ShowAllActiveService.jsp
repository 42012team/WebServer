<%@ page import="classes.model.ActiveService" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.ServiceStatus" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 06.02.2017
  Time: 23:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form  >
    <%List<ActiveService> activeServiceList= (List<ActiveService>) request.getAttribute("activeServiceList");
        List<Service> serviceList= (List<Service>) request.getAttribute("activeServiceDescription");
        for (int k = 0; k < serviceList.size(); k++) {
            Service s = serviceList.get(k);%>
    <p>Услуга номер : <%=k + 1%> </p>
    <p>Название услуги: <%=s.getName()%></p>
    <p>Описание услуги: <%=s.getDescription()%></p>
    <p>Тип услуги:<%=s.getType()%></p>
    <p>Статус услуги:<%=activeServiceList.get(k).getCurrentStatus().toString()%></p>
    <%   if (s.getStatus().equals(ServiceStatus.DEPRECATED)) {%>
    <p> Ваша услуга устарела!<p>
        <% }
        if (activeServiceList.get(k).getNewStatus() != null) {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String strDate = sdfDate.format(activeServiceList.get(k).getDate());%>
    <p> Запланировано изменение статуса услуги на <%= activeServiceList.get(k).getNewStatus().toString()
    %> c  <%=strDate%></p>
    <%
        }
    %>
    <input type="hidden" name="deleteActiveService" value="<%=activeServiceList.get(k).getId()%>"/>
    <input type="submit" formaction="DeleteActiveServiceServlet" formmethod="post" name="deleteActiveServiceButton" value="Удалить"/>
    //cсоздать что-то со старыми значениями и передать на сервер.
    <input type="submit" formmethod="post" name="changeActiveService"/>
    <p>________________________________________________________</p>
    <%
        }
    %>

</form>
</body>
</html>
