<%@ page import="classes.configuration.Initialization" %>
<%@ page import="classes.model.ServiceStatus" %>
<%@ page import="classes.model.Service" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form>
    <p>Название услуги:<input type="text" name="name" value="<%=request.getAttribute("name")%>"></p>
    <p>Описание услуги:<input type="text" name="description" value="<%=request.getAttribute("description")%>"></p>
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
    <input type="submit" formaction="CnangeServiceRespServlet" formmethod="post" value="Изменить услугу"/>
</form>
</body>
</html>
