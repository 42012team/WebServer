package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
import classes.model.ActiveServiceState;
import classes.model.ActiveServiceStatus;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TariffChangeByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = ((ActiveService) request.getSession(true).getAttribute("changedActiveService")).getId();
            int userId = (Integer) request.getSession(true).getAttribute("userForChange");
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String dateToString = request.getParameter("activationDate" + serviceId);
            dateToString = dateToString.replace('T', ' ');
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate = format.parse(dateToString);
            TransmittedActiveServiceParams transmittedActiveServiceParams = TransmittedActiveServiceParams.create()
                    .withActiveServiceId(id)
                    .withRequestType("getActiveServiceById");
            ResponseDTO resp = controller.identifyObject(transmittedActiveServiceParams);
            if (resp.getResponseType().equals("exception"))
                throw new ServletException(((TransmittedException) resp).getMessage());
            ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
            ActiveService activeService = activeServiceResponse.getAllActiveServices().get(0);
            TransmittedActiveServiceParams addActiveServiceParams = TransmittedActiveServiceParams.create()
                    .withOldActiveServiceId(activeService.getId())
                    .withServiceId(serviceId)
                    .withUserId(userId)
                    .withDate(newDate)
                    .withFirstStatus(ActiveServiceStatus.PLANNED)
                    .withSecondStatus(ActiveServiceStatus.ACTIVE)
                    .withState(ActiveServiceState.NOT_READY)
                    .withRequestType("changeTariffActiveService");
            resp = controller.identifyObject(addActiveServiceParams);
            if (resp.getResponseType().equals("exception"))
                throw new ServletException(((TransmittedException) resp).getMessage());
            activeServiceResponse = (ActiveServiceResponse) resp;
            int newId = activeServiceResponse.getAllActiveServices().get(0).getId();
            if (activeService.getState().equals(ActiveServiceState.READY)) {
                TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                        .withActiveServiceId(id)
                        .withServiceId(activeService.getServiceId())
                        .withUserId(userId)
                        .withDate(newDate)
                        .withFirstStatus(activeService.getSecondStatus())
                        .withSecondStatus(ActiveServiceStatus.DISCONNECTED)
                        .withVersion(activeService.getVersion())
                        .withNextActiveServiceId(newId)
                        .withState(activeService.getState())
                        .withRequestType("changeActiveService");
                resp = controller.identifyObject(activeServiceParams);
                if (resp.getResponseType().equals("exception"))
                    throw new ServletException(((TransmittedException) resp).getMessage());
            } else {
                TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                        .withActiveServiceId(id)
                        .withUserId(userId)
                        .withServiceId(activeService.getServiceId())
                        .withDate(newDate)
                        .withFirstStatus(activeService.getFirstStatus())
                        .withSecondStatus(ActiveServiceStatus.DISCONNECTED)
                        .withVersion(activeService.getVersion())
                        .withState(ActiveServiceState.NOT_READY)
                        .withRequestType("changeActiveService");
                resp = controller.identifyObject(activeServiceParams);
                if (resp.getResponseType().equals("exception"))
                    throw new ServletException(((TransmittedException) resp).getMessage());
            }
            response.sendRedirect("/ChangeUserInfoByAdminServlet?user_id=" + userId);
        } catch (ParseException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        }
    }

}
