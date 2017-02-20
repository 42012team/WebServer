package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
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
        String info = request.getParameter("activeServiceParams");
        String[] activeServiceElement = info.split(";");
        System.out.println(info+"info");
        String date = (String) request.getParameter("date");
        User user = (User) request.getSession(true).getAttribute("user");
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date newDate = null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ActiveServiceStatus currentStatus = null;
        ActiveServiceStatus newStatus = null;
        System.out.println(activeServiceElement[3]);
        switch (activeServiceElement[3]) {
            case "ACTIVE": {
                newStatus = ActiveServiceStatus.ACTIVE;
                break;
            }
            case "SUSPENDED": {
                newStatus = ActiveServiceStatus.SUSPENDED;
                break;
            }


        }
        switch (activeServiceElement[2]) {
            case "ACTIVE": {
                currentStatus = ActiveServiceStatus.ACTIVE;
                break;
            }
            case "SUSPENDED": {
                currentStatus = ActiveServiceStatus.SUSPENDED;
                break;
            }
            case "PLANNED": {
                currentStatus = ActiveServiceStatus.PLANNED;
                break;

            }
        }

        TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(Integer.parseInt(activeServiceElement[0]))
                .withUserId(user.getId())
                .withDate(newDate)
                .withCurrentStatus(currentStatus)
                .withNewStatus(newStatus)
                .withVersion(Integer.parseInt(activeServiceElement[5]))
                .withRequestType("changeActiveService");
        controller.indentifyObject(activeServiceParams);
        request.getRequestDispatcher("/ShowActiveServicesServlet").forward(request, response);
    }

}