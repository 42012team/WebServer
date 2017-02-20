
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>d
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="changeActiveServiceStyle.css" rel="stylesheet">
    <script src="activeServiscesEffect.js"></script>
</head>
<body onload="load()">
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
                <li><a href="/ShowAllServicesServlet">Услуги</a></li>
                <li><a href="/ShowProfilePageServlet" color="blue" class="settings">Вернуться в профиль</a></li>
                <li><a href="/ShowAllowedToConnectServiceServlet" color="blue" class="settings">Подключить услугу</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<form method="post" action="ChangeActiveServiceRespServlet">
    <div class="container">
        <div class="row">
            <div class="col-md-6 text-center">
                <div class="box">
                    <div class="box-content">
                        <h2 class="tag-title"> Изменение даты:</h2>
                        <hr/>
                        <%
                            String info = (String) request.getAttribute("activeService");
                            StringBuffer newInfo = new StringBuffer();
                        %>

                        <%
                            String[] activeServiceElement = info.split(";");
                            if (!activeServiceElement[3].equals("null")) {
                                if (activeServiceElement[3].equals("ACTIVE")) {
                        %>
                        <p>Введите новую дату подключения в формате:</p><p ><strong>ДД.ММ.ГГГГ ЧЧ:ММ</strong></p>

                        <%
                            }
                            if (activeServiceElement[3].equals("SUSPENDED")) {
                        %>
                        <p>Введите новую дату блокировки:</p><p ><strong>ДД.ММ.ГГГГ ЧЧ:ММ</strong></p>

                        <%
                            }
                        } else {%>
                        <p>Введите дату блокировки:</p><p ><strong>ДД.ММ.ГГГГ ЧЧ:ММ</strong></p>

                        <% activeServiceElement[3] = "SUSPENDED";}
                            for (int i = 0; i < activeServiceElement.length; i++) {
                                newInfo.append(activeServiceElement[i]);
                                newInfo.append(";");
                            }


                        %>

                        <input type="hidden" name="activeServiceParams" value="<%=newInfo.toString()%>"/>
                        <input type="text" name="date" class="date" placeholder="Введите новую дату"/>
                        <input type="submit" class="changeButton" value="Применить"/>
                    </div>
                </div>
            </div>
        </div>
    </div>

</form>
</body>
</html>
