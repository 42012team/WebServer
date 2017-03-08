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
public class TariffChangeByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = (Integer) request.getSession(true).getAttribute("changedActiveServiceId");
            int user_id = (Integer)request.getSession(true).getAttribute("userForChange");
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String dateToString = request.getParameter("activationDate" + serviceId);
            dateToString = dateToString.replace('T', ' ');
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate = null;
            newDate = format.parse(dateToString);

            //получили
            TransmittedActiveServiceParams transmittedActiveServiceParams = TransmittedActiveServiceParams.create()
                    .withActiveServiceId(id)
                    .withRequestType("getActiveServiceById");
            ResponseDTO resp = controller.identifyObject(transmittedActiveServiceParams);
            if (resp.getResponseType().equals("exception"))
                throw new ServletException(((TransmittedException) resp).getMessage());
            ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
            ActiveService activeService = activeServiceResponse.getAllActiveServices().get(0);
            //поменяли
            TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                    .withActiveServiceId(id)
                    .withUserId(user_id)
                    .withDate(newDate)
                    .withCurrentStatus(activeService.getCurrentStatus())
                    .withNewStatus(ActiveServiceStatus.DISCONNECTED)
                    .withVersion(activeService.getVersion())
                    .withRequestType("changeActiveService");
            resp = controller.identifyObject(activeServiceParams);
            if (resp.getResponseType().equals("exception"))
                throw new ServletException(((TransmittedException) resp).getMessage());


            TransmittedActiveServiceParams addActiveServiceParams = TransmittedActiveServiceParams.create()
                    .withServiceId(serviceId)
                    .withUserId(user_id)
                    .withDate(newDate)
                    .withCurrentStatus(ActiveServiceStatus.PLANNED)
                    .withNewStatus(ActiveServiceStatus.ACTIVE)
                    .withRequestType("changeTariffActiveService");
            resp = controller.identifyObject(addActiveServiceParams);
            if (resp.getResponseType().equals("exception"))
                throw new ServletException(((TransmittedException) resp).getMessage());
           response.sendRedirect("/ChangeUserInfoByAdminServlet?user_id="+user_id);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
