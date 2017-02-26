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
        if (request.getParameter("cancelLock") != null) {
            activeService.setNewStatus(null);

        } else {
            String date = (String) request.getParameter("date");
            date = date.replace('T', ' ');
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                newDate = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (activeService.getNewStatus() != null)
                switch (activeService.getNewStatus()) {
                    case ACTIVE: {
                        activeService.setNewStatus(ActiveServiceStatus.ACTIVE);
                        break;
                    }
                    case SUSPENDED: {
                        activeService.setNewStatus(ActiveServiceStatus.SUSPENDED);
                        break;
                    }


                }
            switch (activeService.getCurrentStatus()) {
                case ACTIVE: {
                    activeService.setCurrentStatus(ActiveServiceStatus.ACTIVE);
                    break;
                }
                case SUSPENDED: {
                    activeService.setCurrentStatus(ActiveServiceStatus.SUSPENDED);
                    break;
                }
                case PLANNED: {
                    activeService.setCurrentStatus(ActiveServiceStatus.PLANNED);
                    break;

                }
            }
        }
        TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(activeService.getId())
                .withUserId(activeService.getUserId())
                .withDate(newDate)
                .withCurrentStatus(activeService.getCurrentStatus())
                .withNewStatus(activeService.getNewStatus())
                .withVersion(activeService.getVersion())
                .withRequestType("changeActiveService");
        ResponseDTO resp = controller.identifyObject(activeServiceParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        response.sendRedirect("/ShowActiveServicesByAdminServlet");
    }

}