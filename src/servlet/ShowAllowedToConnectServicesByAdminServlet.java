package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.request.impl.TransmittedServiceParams;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession(true).getAttribute("userForChange");
        ServiceResponse serviceResponse = (ServiceResponse) controller.indentifyObject(TransmittedServiceParams.create().
                withUserId(userId).withRequestType("allowedToConnect"));
        System.out.println(serviceResponse.getServices().size());
        request.setAttribute("allowedToConnectServices", serviceResponse.getServices());
        request.getRequestDispatcher("allowedToConnectServicesByAdminPage.jsp").forward(request, response);
    }

}
