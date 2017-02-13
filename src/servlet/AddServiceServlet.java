package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.model.ServiceStatus;
import classes.request.impl.TransmittedServiceParams;
import classes.response.impl.ServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServiceServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceResponse serviceResponse= (ServiceResponse) controller.indentifyObject(TransmittedServiceParams.create()
                .withName(request.getParameter("name"))
                .withDescription(request.getParameter("description"))
                .withType(request.getParameter("type"))
                .withStatus(ServiceStatus.ALLOWED)
                .withRequestType("createService"));
        request.getRequestDispatcher("AllServicesServlet").forward(null,null);
    }

}
