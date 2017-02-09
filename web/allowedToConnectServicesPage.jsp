<%@ page import="java.util.List" %>
<%@ page import="classes.model.Service" %><%--
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
<% List<Service> serviceList= (List<Service>) request.getAttribute("allowedToConnectServices");
    int j=1;
    for (Service s : serviceList) {%>
       <p>_______________________________________________________</p>
       <p>Услуга номер <%=j%></p>
        <p>Название услуги:<%=s.getName()%></p>
        <p>Описание услуги:<%=s.getDescription()%></p>
        <p>Тип услуги:<%=s.getType()%>
       <p>_______________________________________________________</p>

      <%  j++;
    }
%>
</body>
</html>
