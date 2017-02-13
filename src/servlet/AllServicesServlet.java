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

public class AllServicesServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceResponse serviceResponse= (ServiceResponse) controller.indentifyObject(TransmittedServiceParams.create()
                .withRequestType("allServices"));
        request.setAttribute("allServices",serviceResponse.getServices());
        request.getRequestDispatcher("allServicesPage.jsp").forward(request, response);
    }

}
