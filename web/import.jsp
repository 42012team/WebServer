<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form  action="ImportServlet" method="post">
  <p>Введите путь файла<input type="text" name="path"/></p>
    <input type="submit" name="" value="OK"/>
</form>
</body>
</html>
