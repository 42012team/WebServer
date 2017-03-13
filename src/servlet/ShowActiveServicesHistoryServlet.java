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


public class ShowActiveServicesHistoryServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResponseDTO resp = controller.identifyObject(TransmittedActiveServiceParams.create()
                .withUserId(Integer.parseInt(request.getParameter("user_id")))
                .withServiceId(Integer.parseInt(request.getParameter("service_id")))
                .withRequestType("getHistory"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        resp = controller.identifyObject(TransmittedServiceParams.create()
                .withUserId(Integer.parseInt(request.getParameter("user_id")))
                .withServiceId(Integer.parseInt(request.getParameter("service_id")))
                .withRequestType("getHistoryDescriptions"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ServiceResponse serviceResponse = (ServiceResponse) resp;
        List<Service> serviceList = serviceResponse.getServices();
        request.setAttribute("activeServicesDescriptions", serviceList);
        request.setAttribute("activeServicesList", activeServicesList);
        request.getRequestDispatcher("/showHistoryPage.jsp").forward(request, response);
    }

}
