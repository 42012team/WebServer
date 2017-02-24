package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.model.ActiveService;
import classes.model.ActiveServiceStatus;
import classes.model.User;
import classes.request.impl.TransmittedActiveServiceParams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 18.02.2017.
 */
public class ChangeActiveServiceRespServlet extends HttpServlet

{
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActiveService activeService= (ActiveService) request.getSession(true).getAttribute("changedActiveService");
        String date = (String) request.getParameter("date");
        User user = (User) request.getSession(true).getAttribute("user");
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(activeService.getNewStatus()!=null)
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

        TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(activeService.getId())
                .withUserId(user.getId())
                .withDate(newDate)
                .withCurrentStatus(activeService.getCurrentStatus())
                .withNewStatus(activeService.getNewStatus())
                .withVersion(activeService.getVersion())
                .withRequestType("changeActiveService");
        controller.indentifyObject(activeServiceParams);
        response.sendRedirect("/ShowActiveServicesServlet");
    }

}