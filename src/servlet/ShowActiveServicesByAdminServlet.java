package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
import classes.model.Service;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;
import classes.response.impl.ServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ShowActiveServicesByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession(true).getAttribute("userForChange");
        ResponseDTO resp = controller.identifyObject(TransmittedActiveServiceParams.create().
                withUserId(userId)
                .withRequestType("allActiveServices"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        resp = controller.identifyObject(TransmittedServiceParams.create().
                withUserId(userId)
                .withRequestType("activeServicesDescriptions"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ServiceResponse serviceResponse = (ServiceResponse) resp;
        List<Service> serviceList = serviceResponse.getServices();
        request.setAttribute("activeServiceDescription", serviceList);
        request.setAttribute("activeServiceList", activeServicesList);
        request.getRequestDispatcher("/showActiveServicesByAdminPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResponseDTO resp = controller.identifyObject(TransmittedActiveServiceParams.create().
                withUserId(Integer.parseInt(request.getParameter("chooseUser")))
                .withRequestType("allActiveServices"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        resp = controller.identifyObject(TransmittedServiceParams.create().
                withUserId(Integer.parseInt(request.getParameter("chooseUser")))
                .withRequestType("activeServicesDescriptions"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ServiceResponse serviceResponse = (ServiceResponse) resp;
        List<Service> serviceList = serviceResponse.getServices();
        request.setAttribute("activeServiceDescription", serviceList);
        request.setAttribute("activeServiceList", activeServicesList);
        request.getSession(true).setAttribute("userForChange", Integer.parseInt(request.getParameter("chooseUser")));
        request.getRequestDispatcher("/showActiveServicesByAdminPage.jsp").forward(request, response);
    }

}
