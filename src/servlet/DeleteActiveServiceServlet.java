package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.User;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class DeleteActiveServiceServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession(true).getAttribute("user");
        TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(Integer.parseInt(request.getParameter("chooseActiveService")))
                .withUserId(user.getId())
                .withUnlockingTime((new Date()).getTime() - 3000)
                .withRequestType("deleteActiveService");
        ResponseDTO resp = controller.identifyObject(activeServiceParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        response.sendRedirect("/WebServer_war_exploded/ShowActiveServicesServlet");

    }
}
