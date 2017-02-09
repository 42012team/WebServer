<%@ page import="java.util.List" %>
<%@ page import="classes.model.Service" %>
<%@ page import="classes.model.User" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 09.02.2017
  Time: 19:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="AddActiveServiceServlet" method="post">
<% List<Service> serviceList= (List<Service>) request.getAttribute("allowedToConnectServices");
    int j=1;
    User user= (User) session.getAttribute("user");
    for (Service s : serviceList) {%>
       <p>_______________________________________________________</p>
       <p>Услуга номер <%=j%></p>
        <p>Название услуги:<%=s.getName()%></p>
        <p>Описание услуги:<%=s.getDescription()%></p>
        <p>Тип услуги:<%=s.getType()%>
       <p>_______________________________________________________</p>
        <input type="hidden" name="serviceId" value="<%=s.getId()%>"/>
       <input type="hidden" name="userId" value="<%=user.getId()%>">
        <input type="text" name="activateDate"/>
        <input type="submit" name="addActiveService" value="Добавить"/>
      <%  j++;
    }
%>
    </form>
</body>
</html>
