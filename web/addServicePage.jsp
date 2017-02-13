<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form>
    <p>Название услуги:<input type="text" name="name" value=""></p>
    <p>Описание услуги:<input type="text" name="description" value=""></p>
    <p>Тип услуги:<input type="text" name="type" value=""></p>
    <p>Статус услуги:
    <input type="submit" formaction="AddServiceServlet" formmethod="post" value="Добавить услугу"/>
</form>
</body>
</html>
