package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.model.ServiceStatus;
import classes.request.impl.TransmittedServiceParams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CnangeServiceRespServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TransmittedServiceParams serviceParams=TransmittedServiceParams.create()
                .withServiceId(Integer.parseInt(request.getParameter("serviceId")))
                .withName(request.getParameter("name"))
                .withDescription(request.getParameter("description"))
                .withVersion(Integer.parseInt(request.getParameter("version")))
                .withRequestType("changeService");
        switch (request.getParameter("status")){
            case "ALLOWED":
                serviceParams.withStatus(ServiceStatus.ALLOWED);
                controller.indentifyObject(serviceParams);
                break;
            case "DEPRECATED":
                serviceParams.withStatus(ServiceStatus.DEPRECATED);
                controller.indentifyObject(serviceParams);
                break;
        }
        request.getRequestDispatcher("AllServicesServlet").forward(request, response);
    }
}