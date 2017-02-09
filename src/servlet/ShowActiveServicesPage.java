package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.model.ActiveService;
import classes.model.Service;
import classes.model.User;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedServiceParams;
import classes.response.impl.ActiveServiceResponse;
import classes.response.impl.ServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by User on 08.02.2017.
 */
public class ShowActiveServicesPage extends HttpServlet {
    WebController controller=null;
    @Override
    public void init() throws ServletException {
        controller= Initialization.getInstance().initialization();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession(true).getAttribute("user");
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse)
                controller.indentifyObject(TransmittedActiveServiceParams.create().
                        withUserId(user.getId()).withRequestType("allActiveServices"));
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        ServiceResponse serviceResponse = (ServiceResponse) controller.indentifyObject(TransmittedServiceParams.create().
                withUserId(user.getId()).withRequestType("activeServicesDescriptions"));
        List<Service> serviceList = serviceResponse.getServices();
        request.setAttribute("activeServiceDescription", serviceList);
        request.setAttribute("activeServiceList", activeServicesList);
        request.getRequestDispatcher("showAllActiveService.jsp").forward(request, response);
    }

}
