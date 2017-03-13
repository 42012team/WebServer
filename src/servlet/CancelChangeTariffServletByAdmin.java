package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by User on 09.03.2017.
 */
public class CancelChangeTariffServletByAdmin extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActiveService activeService = (ActiveService) request.getSession(true).getAttribute("changedActiveService");
        TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(activeService.getId())
                .withUserId(activeService.getUserId())
                .withUnlockingTime((new Date()).getTime() - 3000)
                .withRequestType("cancelChangeTariff");
        ResponseDTO resp = controller.identifyObject(activeServiceParams);
        request.setAttribute("user_id", activeService.getUserId());
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        response.sendRedirect("/ChangeUserInfoByAdminServlet?user_id=" + activeService.getUserId());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActiveService activeService = (ActiveService) request.getSession(true).getAttribute("changedActiveService");
        TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(activeService.getId())
                .withUserId(activeService.getUserId())
                .withUnlockingTime((new Date()).getTime() - 3000)
                .withRequestType("cancelChangeTariff");
        ResponseDTO resp = controller.identifyObject(activeServiceParams);
        System.out.println(activeService.getCurrentStatus()+"CURRENTSTATUS");
        activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(activeService.getId())
                .withUserId(activeService.getUserId())
                .withDate(activeService.getDate())
                .withCurrentStatus(activeService.getCurrentStatus())
                .withNewStatus(activeService.getNewStatus())
                .withVersion(activeService.getVersion())
                .withRequestType("changeActiveService");
        resp = controller.identifyObject(activeServiceParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        request.setAttribute("user_id", activeService.getUserId());
        response.sendRedirect("/ChangeUserInfoByAdminServlet?user_id=" + activeService.getUserId());
    }
}
