package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveServiceStatus;
import classes.model.User;
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

public class AddActiveServiceServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = (User) request.getSession(true).getAttribute("user");
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String dateToString = request.getParameter("activationDate" + serviceId);
            dateToString = dateToString.replace('T', ' ');
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate = null;
            newDate = format.parse(dateToString);
            if(newDate.before(new Date())&&(!((User)request.getSession(true).getAttribute("user")).getPrivilege().equals("admin"))){
                throw new ServletException("НЕВЕРНЫЙ ВВОД ДАТЫ!");
            }
            TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                    .withServiceId(serviceId)
                    .withUserId(user.getId())
                    .withDate(newDate)
                    .withCurrentStatus(ActiveServiceStatus.PLANNED)
                    .withNewStatus(ActiveServiceStatus.ACTIVE)
                    .withRequestType("createActiveService");
            ResponseDTO resp = controller.identifyObject(activeServiceParams);
            if (resp.getResponseType().equals("exception"))
                throw new ServletException(((TransmittedException) resp).getMessage());
            response.sendRedirect("/ShowActiveServicesServlet");
        } catch (ParseException e) {
            throw new ServletException("НЕКОРРЕКТНАЯ ДАТА!");
        }
    }
}
