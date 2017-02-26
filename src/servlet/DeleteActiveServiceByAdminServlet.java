package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.request.impl.TransmittedActiveServiceParams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class DeleteActiveServiceByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (int) request.getSession(true).getAttribute("userForChange");
        TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(Integer.parseInt(request.getParameter("chooseActiveService")))
                .withUserId(userId)
                .withUnlockingTime((new Date()).getTime() - 3000)
                .withRequestType("deleteActiveService");
        controller.indentifyObject(activeServiceParams);
        response.sendRedirect("/ShowActiveServicesByAdminServlet");
    }

}
