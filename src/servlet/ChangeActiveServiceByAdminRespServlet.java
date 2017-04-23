package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
import classes.model.ActiveServiceStatus;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeActiveServiceByAdminRespServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Date newDate = null;
        ActiveService activeService = (ActiveService) request.getSession(true).getAttribute("changedActiveService");
        TransmittedActiveServiceParams activeServiceParams = null;
        if (request.getParameter("cancelLock") != null) {
            activeServiceParams = TransmittedActiveServiceParams.create()
                    .withActiveServiceId(activeService.getId())
                    .withRequestType("cancelLock");
        } else {
            String date = (String) request.getParameter("date");
            date = date.replace('T', ' ');
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                newDate = format.parse(date);
            } catch (ParseException e) {
                throw new ServletException("НЕВЕРНЫЙ ВВОД ДАТЫ!");
            }
            if (activeService.getSecondStatus() != null)
                switch (activeService.getSecondStatus()) {
                    case ACTIVE: {
                        activeService.setSecondStatus(ActiveServiceStatus.ACTIVE);
                        break;
                    }
                    case SUSPENDED: {
                        activeService.setSecondStatus(ActiveServiceStatus.SUSPENDED);
                        break;
                    }


                }
            switch (activeService.getFirstStatus()) {
                case ACTIVE: {
                    activeService.setFirstStatus(ActiveServiceStatus.ACTIVE);
                    break;
                }
                case SUSPENDED: {
                    activeService.setFirstStatus(ActiveServiceStatus.SUSPENDED);
                    break;
                }
                case PLANNED: {
                    activeService.setFirstStatus(ActiveServiceStatus.PLANNED);
                    break;

                }
            }
            activeServiceParams = TransmittedActiveServiceParams.create()
                    .withActiveServiceId(activeService.getId())
                    .withUserId(activeService.getUserId())
                    .withServiceId(activeService.getServiceId())
                    .withDate(newDate)
                    .withFirstStatus(activeService.getFirstStatus())
                    .withSecondStatus(activeService.getSecondStatus())
                    .withVersion(activeService.getVersion())
                    .withState(activeService.getState())
                    .withRequestType("changeActiveService");
        }
        ResponseDTO resp = controller.identifyObject(activeServiceParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        response.sendRedirect("/WebServer_war_exploded/ChangeUserInfoByAdminServlet?user_id=" + activeService.getUserId());
    }

}