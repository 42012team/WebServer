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


public class ActionWithActiveServiceServlet  extends HttpServlet{
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("chooseActiveService"));
        TransmittedActiveServiceParams transmittedActiveServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(id)
                .withRequestType("getActiveServiceById");
        ResponseDTO resp = controller.identifyObject(transmittedActiveServiceParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        ActiveService activeService = activeServiceResponse.getAllActiveServices().get(0);
        resp = controller.identifyObject(TransmittedServiceParams.create()
                .withServiceId(activeService.getServiceId())
                .withRequestType("getServiceById"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        Service service = ((ServiceResponse) resp).getServices().get(0);
        request.setAttribute("activeServiceForChanging", activeService);
        request.setAttribute("service",service);
        request.getRequestDispatcher("/ActionWithActiveService.jsp").forward(request, response);
    }

}