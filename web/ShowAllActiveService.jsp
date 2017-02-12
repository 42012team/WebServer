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
<form>
    <%
        List<ActiveService> activeServiceList = (List<ActiveService>) request.getAttribute("activeServiceList");
        List<Service> serviceList = (List<Service>) request.getAttribute("activeServiceDescription");
        for (int k = 0; k < serviceList.size(); k++) {
            Service s = serviceList.get(k);
    %>
    <p><input type="radio" name="chooseActiveService" value="<%=activeServiceList.get(k).getId()%>"/> Услуга номер
        : <%=k + 1%>
    </p>

    <p>Название услуги: <%=s.getName()%>
    </p>
    <p>Описание услуги: <%=s.getDescription()%>
    </p>
    <p>Тип услуги:<%=s.getType()%>
    </p>
    <p>Статус услуги:<%=activeServiceList.get(k).getCurrentStatus().toString()%>
    </p>
    <% if (s.getStatus().equals(ServiceStatus.DEPRECATED)) {%>
    <p> Ваша услуга устарела!
    <p>
            <% }
        if (activeServiceList.get(k).getNewStatus() != null) {

            SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            String strDate = sdfDate.format(activeServiceList.get(k).getDate());%>
    <p> Запланировано изменение статуса услуги на <%= activeServiceList.get(k).getNewStatus().toString()
    %> c  <%=strDate%>
    </p>
    <%
        }
      //  StringBuffer stringBuffer=new StringBuffer()
    %>
    <input type="hidden" name="<%=activeServiceList.get(k).getId()%>" value=""/>

    <p>________________________________________________________</p>
    <%
        }
    %>
    <input type="submit" name="deleteActiveServiceButton" formaction="DeleteActiveServiceServlet" formmethod="post"
           value="Удалить услугу"/>
    <input type="submit" name="changeActiveService" formaction="ChangeActiveServiceServlet" formmethod="post"
           value="Изменить выбранную услугу"/>
    <input type="submit" name="addActiveService" formaction="ShowAllowedToConnectServiceServlet" formmethod="post"
           value="Подключить услугу"/>
    <input type="submit" name="inProfile" formaction="ShowProfilePageServlet" formmethod="post"
           value="Вернуться в профиль"/>
</form>
</body>
</html>
