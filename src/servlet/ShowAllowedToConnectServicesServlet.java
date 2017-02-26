package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.User;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAllowedToConnectServicesServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession(true).getAttribute("user");
        ResponseDTO resp = controller.identifyObject(TransmittedServiceParams.create().
                withUserId(user.getId()).withRequestType("allowedToConnect"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ServiceResponse serviceResponse = (ServiceResponse) resp;
        request.setAttribute("allowedToConnectServices", serviceResponse.getServices());
        request.getRequestDispatcher("allowedToConnectServicesPage.jsp").forward(request, response);
    }

}
