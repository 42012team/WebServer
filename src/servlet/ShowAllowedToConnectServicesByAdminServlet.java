package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAllowedToConnectServicesByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession(true).getAttribute("userForChange");
        ResponseDTO resp = controller.identifyObject(TransmittedServiceParams.create().
                withUserId(userId).withRequestType("allowedToConnect"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ServiceResponse serviceResponse = (ServiceResponse) resp;
        request.removeAttribute("allowedToConnectServices");
        request.setAttribute("allowedToConnectServices", serviceResponse.getServices());
        request.getRequestDispatcher("/WebServer_war_exploded/allowedToConnectServicesByAdminPage.jsp").forward(request, response);
    }

}
