package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
import classes.model.ActiveServiceStatus;
import classes.model.User;
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

/**
 * Created by User on 07.03.2017.
 */
public class TariffChangeServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = (Integer) request.getSession(true).getAttribute("changedActiveServiceId");
            User user = (User) request.getSession(true).getAttribute("user");
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String dateToString = request.getParameter("activationDate" + serviceId);
            dateToString = dateToString.replace('T', ' ');
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate = null;
            newDate = format.parse(dateToString);
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
                    .withUserId(user.getId())
                    .withDate(newDate)
                    .withCurrentStatus(ActiveServiceStatus.PLANNED)
                    .withNewStatus(ActiveServiceStatus.ACTIVE)
                    .withRequestType("changeTariffActiveService");
            resp = controller.identifyObject(addActiveServiceParams);
            if (resp.getResponseType().equals("exception"))
                throw new ServletException(((TransmittedException) resp).getMessage());
            TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                    .withActiveServiceId(id)
                    .withUserId(user.getId())
                    .withDate(newDate)
                    .withCurrentStatus(activeService.getCurrentStatus())
                    .withNewStatus(ActiveServiceStatus.DISCONNECTED)
                    .withVersion(activeService.getVersion())
                    .withRequestType("changeActiveService");
            resp = controller.identifyObject(activeServiceParams);
            if (resp.getResponseType().equals("exception"))
                throw new ServletException(((TransmittedException) resp).getMessage());

            response.sendRedirect("/ShowActiveServicesServlet");
        } catch (ParseException ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        }
    }
}
