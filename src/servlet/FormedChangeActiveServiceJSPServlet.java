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
public class FormedChangeActiveServiceJSPServlet extends HttpServlet

    {
        WebController controller = null;

        @Override
        public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            String info= (String) request.getParameter("activeServiceParams");
            System.out.println(info+"lalala");
            String[] activeServiceElement=info.split(";");
            String date= (String) request.getParameter("date");
            System.out.println(date);
            User user= (User) request.getSession(true).getAttribute("user");
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date newDate = null;
            try {
                newDate = format.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(newDate+"newDate");
            System.out.println(activeServiceElement[2]+" "+activeServiceElement[3]);
            ActiveServiceStatus currentStatus=null;
            ActiveServiceStatus newStatus=null;
            if(!activeServiceElement[3].equals(null)){
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
                case "PLANNED":{
                    currentStatus=ActiveServiceStatus.PLANNED;
                    break;

                }
            }
            System.out.println(currentStatus+" "+newStatus);

            TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                    .withActiveServiceId(Integer.parseInt(activeServiceElement[0]))
                    .withUserId(user.getId())
                    .withDate(newDate)
                    .withCurrentStatus(currentStatus)
                    .withNewStatus(newStatus)
                    .withVersion(Integer.parseInt(activeServiceElement[5]))
                    .withRequestType("changeActiveService");
            controller.indentifyObject(activeServiceParams);
            request.getRequestDispatcher("/ShowActiveServicesPage").forward(request, response);
        }

}
