<%@ page import="classes.model.ActiveService" %>
<%@ page import="classes.model.ActiveServiceStatus" %>
<%@ page import="classes.model.Service" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <div id="usersActiveServices"><span id="connectService"><h2>Подключенные услуги</h2></span></div>
    <ul>
        <div class="container">
            <%
                List<ActiveService> activeServicesList = (List<ActiveService>) request.getAttribute("activeServicesList");
                List<Service> allServices = (List<Service>) request.getAttribute("activeServicesDescriptions");
                System.out.println("размер:"+allServices.size());
                if (allServices.size() > 0) {%>
            <p>
            <h2 class="text-center">Услуги типа <%=allServices.get(0).getType()%>
            </h2>
            </p>
            <div class="row">
                <div class="col-md-4 text-center">
                    <div class="box">
                        <div class="box-content">
                            <li>
                                <%
                                    int num = 0;
                                    int serviceId = 0;
                                    String type = allServices.get(0).getType();
                                    boolean isSecond = false;
                                    boolean bool = isSecond;
                                    for (int j = 0; j < activeServicesList.size(); j++) {
                                        boolean hasFind = false;
                                        for (int k = 0; k < allServices.size(); k++) {
                                            if ((activeServicesList.get(j).getServiceId() == allServices.get(k).getId()) &&
                                                    (allServices.get(k).getType().equals(type))) {
                                                if (!isSecond) {
                                                    num = j;
                                                    serviceId = k;
                                                    hasFind = true;
                                                    break;
                                                } else {
                                                    isSecond = false;
                                                    break;
                                                }
                                            }
                                        }
                                        if (hasFind)
                                            break;
                                    }%>
                                <h2 class="tag-title"><span
                                        class="value"><%=allServices.get(serviceId).getName()%></span></h2>
                                <hr/>
                                <div class="description">Описание услуги:<span
                                        class="value"><%=allServices.get(serviceId).getDescription()%></span>
                                </div>
                                <div class="description">Тип услуги: <span
                                        class="value"><%=allServices.get(serviceId).getType()%></span></div>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=activeServicesList.get(num).getFirstStatus().toString()%></span>
                                </div>
                                <% if (activeServicesList.get(num).getSecondStatus() != null) {
                                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    String strDate = sdfDate.format(activeServicesList.get(num).getDate());%>
                                <div class="description">Запланировано изменение статуса услуги на<span
                                        class="value">
    <%= activeServicesList.get(num).getSecondStatus().toString()
    %> c  <%=strDate%>
            </span></div>
                                <%} else {%>
                                <br/>
                                <br/>
                                <%
                                    }
                                    if ((activeServicesList.get(num).getSecondStatus() == null) || ((activeServicesList.get(num).getSecondStatus() != null) && (!activeServicesList.get(num).getSecondStatus().equals(ActiveServiceStatus.DISCONNECTED))) ||
                                            ((activeServicesList.get(num).getSecondStatus() != null) && (activeServicesList.get(num).getDate().compareTo(new Date()) <= 0)) && (activeServicesList.get(num).getSecondStatus().equals(ActiveServiceStatus.DISCONNECTED))) {
                                %></li>
                            <%} else {%>
                            </li><%
                            }
                        %>
                            <br/>
                        </div>
                    </div>
                </div>
                <% if (allServices.size() > 1) {
                    for (int i = 1; i < allServices.size(); i++) {
                %>
                <% if (!allServices.get(i).getType().equals(allServices.get(i - 1).getType())) {%>
            </div>
            <p>
            <h2 class="text-center">Услуги типа <%=allServices.get(i).getType()%>
            </h2>
            </p>
            <div class="row">
                <%}%>
                <div class="col-md-4 text-center">
                    <div class="box">
                        <div class="box-content">
                            <li>
                                <%
                                    num = 0;
                                    type = allServices.get(i).getType();
                                    isSecond = false;
                                    if (allServices.get(i).getType().equals(allServices.get(i - 1).getType()))
                                        isSecond = true;
                                    bool = isSecond;
                                    for (int j = 0; j < activeServicesList.size(); j++) {
                                        boolean hasFind = false;
                                        for (int k = 0; k < allServices.size(); k++) {
                                            if ((activeServicesList.get(j).getServiceId() == allServices.get(k).getId()) &&
                                                    (allServices.get(k).getType().equals(type))) {
                                                if (!isSecond) {
                                                    num = j;
                                                    serviceId = k;
                                                    hasFind = true;
                                                    break;
                                                } else {
                                                    isSecond = false;
                                                    break;
                                                }
                                            }
                                        }
                                        if (hasFind)
                                            break;
                                    }
                                %>
                                <h2 class="tag-title"><span
                                        class="value"><%=allServices.get(serviceId).getName()%></span></h2>
                                <hr/>
                                <%
                                    if (!bool) {
                                %>
                                <%}%>
                                <div class="description">Описание услуги:<span
                                        class="value"><%=allServices.get(serviceId).getDescription()%></span>
                                </div>
                                <div class="description">Тип услуги: <span
                                        class="value"><%=allServices.get(serviceId).getType()%></span></div>
                                <div class="description">Статус услуги: <span
                                        class="value"><%=activeServicesList.get(num).getFirstStatus().toString()%></span>
                                </div>
                                <% if (activeServicesList.get(num).getSecondStatus() != null) {
                                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                                    String strDate = sdfDate.format(activeServicesList.get(num).getDate());%>
                                <div class="description">Запланировано изменение статуса услуги на<span
                                        class="value">
    <%= activeServicesList.get(num).getSecondStatus().toString()
    %> c  <%=strDate%>
            </span></div>
                                <%} else {%>
                                <br/>
                                <br/>
                                <%
                                    }
                                    if ((activeServicesList.get(num).getSecondStatus() == null) || ((activeServicesList.get(num).getSecondStatus() != null) && (!activeServicesList.get(num).getSecondStatus().equals(ActiveServiceStatus.DISCONNECTED))) ||
                                            ((activeServicesList.get(num).getSecondStatus() != null) && (activeServicesList.get(num).getDate().compareTo(new Date()) <= 0)) && (activeServicesList.get(num).getSecondStatus().equals(ActiveServiceStatus.DISCONNECTED))) {
                                %>
                                </li>
                            <%} else {%></li><%
                            }
                        %>
                            <br/>
                        </div>
                    </div>
                </div>
                <% if (i == allServices.size() - 1) {%>
            </div>
            <%}%>
            <%
                        }
                    }
                }
            %>
        </div>
    </ul>
</form>
</body>
</html>
