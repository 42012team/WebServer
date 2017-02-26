package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.Service;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChangeServiceServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TransmittedServiceParams serviceParams = TransmittedServiceParams.create()
                .withRequestType("allServices");
        ResponseDTO resp = controller.identifyObject(serviceParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        List<Service> allServices = ((ServiceResponse) resp).getServices();
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        for (Service service : allServices) {
            if (service.getId() == serviceId) {
                request.setAttribute("version", service.getVersion());
                request.setAttribute("name", service.getName());
                request.setAttribute("description", service.getDescription());
                request.setAttribute("type", service.getType());
                request.setAttribute("status", service.getStatus());
                request.setAttribute("serviceId", service.getId());
                break;
            }
        }
        request.getRequestDispatcher("/changeServicePage.jsp").forward(request, response);
    }
}
