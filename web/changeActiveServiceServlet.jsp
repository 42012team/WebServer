<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 18.02.2017
  Time: 22:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="FormedChangeActiveServiceJSPServlet">

    <%String info= (String) request.getAttribute("activeService");%>
    <input type="hidden" name="activeServiceParams" value="<%=info%>"/>
    <%
        System.out.println(info);
    String[] activeServiceElement=info.split(";");
        if(activeServiceElement[3]!=null){
            if(activeServiceElement[3].equals("ACTIVE")){
                %>
         <p>Введите новую дату подключения:</p>

    <%
            }
        if(activeServiceElement[3].equals("SUSPENDED")){
    %>
    <p>Введите новую дату блокировки:</p>

    <%
            }
        }
        else {%>

    <p>Введите дату блокировки</p>
    <%


            }
    %>
<input type="text" name="date" class="date" placeholder="Введите новую дату"/>
<input type="submit" class="changeButton" value="Применить"/>
    </form>
</body>
</html>
