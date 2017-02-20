package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.request.impl.TransmittedServiceParams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class DeleteServiceServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        controller.indentifyObject(TransmittedServiceParams.create()
                .withServiceId(Integer.parseInt(request.getParameter("serviceId")))
                .withRequestType("deleteService"));
        response.sendRedirect("/adminPage.jsp");
    }
}



