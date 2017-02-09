package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.model.User;
import classes.request.impl.TransmittedServiceParams;
import classes.response.impl.ServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 09.02.2017.
 */
public class ShowAllowedToConnectServiceServlet extends HttpServlet {
    WebController controller = null;
    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user= (User) request.getSession(true).getAttribute("user");
        ServiceResponse serviceResponse= (ServiceResponse) controller.indentifyObject(TransmittedServiceParams.create().
                   withUserId(user.getId()).withRequestType("allowedToConnect"));
        request.setAttribute("allowedToConnectServices",serviceResponse.getServices());
        request.getRequestDispatcher("allowedToConnectServicesPage.jsp").forward(request, response);
    }
}
