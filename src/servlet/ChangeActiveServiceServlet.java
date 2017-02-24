package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.model.ActiveService;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.impl.ActiveServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ChangeActiveServiceServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("chooseActiveService"));
        TransmittedActiveServiceParams transmittedActiveServiceParams=TransmittedActiveServiceParams.create()
                .withActiveServiceId(id)
                .withRequestType("getActiveServiceById");
        ActiveServiceResponse activeServiceResponse= (ActiveServiceResponse) controller.indentifyObject(transmittedActiveServiceParams);
        ActiveService activeService=activeServiceResponse.getAllActiveServices().get(0);
        request.setAttribute("activeService", activeService);
        request.getRequestDispatcher("changeActiveService.jsp").forward(request, response);
    }
}
