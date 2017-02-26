package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.model.ActiveService;
import classes.model.Service;
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


public class ShowActiveServicesByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession(true).getAttribute("userForChange");
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse)
                controller.indentifyObject(TransmittedActiveServiceParams.create().
                        withUserId(userId)
                        .withRequestType("allActiveServices"));
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        ServiceResponse serviceResponse = (ServiceResponse) controller.indentifyObject(TransmittedServiceParams.create().
                withUserId(userId)
                .withRequestType("activeServicesDescriptions"));
        List<Service> serviceList = serviceResponse.getServices();
        request.setAttribute("activeServiceDescription", serviceList);
        request.setAttribute("activeServiceList", activeServicesList);
        request.getRequestDispatcher("showActiveServicesByAdmin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse)
                controller.indentifyObject(TransmittedActiveServiceParams.create().
                        withUserId(Integer.parseInt(request.getParameter("chooseUser")))
                        .withRequestType("allActiveServices"));
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        ServiceResponse serviceResponse = (ServiceResponse) controller.indentifyObject(TransmittedServiceParams.create().
                withUserId(Integer.parseInt(request.getParameter("chooseUser")))
                .withRequestType("activeServicesDescriptions"));
        List<Service> serviceList = serviceResponse.getServices();
        request.setAttribute("activeServiceDescription", serviceList);
        request.setAttribute("activeServiceList", activeServicesList);
        request.getSession(true).setAttribute("userForChange",Integer.parseInt(request.getParameter("chooseUser")));
        request.getRequestDispatcher("showActiveServicesByAdmin.jsp").forward(request, response);
    }

}
