<%@ page import="classes.model.ActiveService" %>
<%@ page import="classes.model.ActiveServiceStatus" %>
<%@ page import="classes.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page errorPage="/errorPage.jsp" %>
<html>d
<head>
    <title>Title</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <link href="changeActiveServiceStyle.css" rel="stylesheet">
    <link href="newStyleForChangeActiveServicePage.css" rel="stylesheet">
    <script src="1.js"></script>
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
                <li><a href="javascript:history.back();"><span class="glyphicon glyphicon-arrow-right">Назад</span></a>
                </li>
                <li><a href="/ShowAllServicesServlet">Все услуги</a></li>
                <li><a href="/ShowProfilePageServlet" color="blue"
                       class="settings"><%=((User) session.getAttribute("user")).getLogin()%>
                </a></li>
                <li><a href="/startPage.jsp">Выйти</a></li>
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
                        <h2 class="tag-title"> Изменение услуги:</h2>
                        <hr/>
                        <%
                            ActiveService activeService = (ActiveService) request.getAttribute("activeService");
                            if (activeService.getNewStatus() != null) {
                                if (activeService.getNewStatus() == ActiveServiceStatus.ACTIVE) {

                        %>
                        <p>Введите новую дату подключения в формате:</p>
                        <p>${errorText}</p>
                        <p><strong>ДД.ММ.ГГГГ ЧЧ:ММ</strong></p>
                        <input type="datetime-local" name="date" class="calendar"/>
                        <input type="submit" class="changeButton" value="Применить"/>
                        <%
                            }
                            if (activeService.getNewStatus() == ActiveServiceStatus.SUSPENDED) {
                        %>

                        <input type="submit" name="cancelLock" class="cancelButton" id="cancel"
                               value="Отменить блокировку"/>
                        <input type="button" class="changeDateButton" onclick="showCalendar()" id="change"
                               name="changeDate" id="changeDate" value="Изменить дату"/>
                        <div style="display:none" name="hiddenText" id="hiddenText"><p>Введите новую дату
                            блокировки:</p>
                            <p><strong>ДД.ММ.ГГГГ ЧЧ:ММ</strong></p>
                        </div>
                        <input type="datetime-local" style="display:none" id="date" name="date" class="calendar"/>
                        <input type="submit" style="display:none" id="submit" class="changeButton" value="Применить"/>
                        <%
                            }
                        } else if (activeService.getCurrentStatus() == ActiveServiceStatus.SUSPENDED) {%>
                        <p>Введите дату разблокировки:</p>
                        <p><strong>ДД.ММ.ГГГГ ЧЧ:ММ</strong></p>
                        <input type="datetime-local" name="date" class="calendar"/>
                        <input type="submit" class="changeButton" value="Применить"/>

                        <% activeService.setNewStatus(ActiveServiceStatus.ACTIVE);

                        } else if (activeService.getCurrentStatus() == ActiveServiceStatus.ACTIVE) {%>
                        <p>Введите дату блокировки:</p>
                        <p><strong>ДД.ММ.ГГГГ ЧЧ:ММ</strong></p>
                        <input type="datetime-local" name="date" class="calendar"/>
                        <input type="submit" class="changeButton" value="Применить"/>

                        <% activeService.setNewStatus(ActiveServiceStatus.SUSPENDED);
                        }
                            session.setAttribute("changedActiveService", activeService);

                        %>

                    </div>
                </div>
            </div>
        </div>
    </div>

</form>
</body>
</html>
