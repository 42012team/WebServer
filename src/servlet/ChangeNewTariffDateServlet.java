package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
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

public class ChangeNewTariffDateServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = (Integer) request.getSession(true).getAttribute("changedActiveServiceId");
        String date = (String) request.getParameter("date");
        date = date.replace('T', ' ');
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date newDate=null;
        try {
            newDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(newDate.before(new Date())&&(!((User)request.getSession(true).getAttribute("user")).getPrivilege().equals("admin"))){
            throw new ServletException("НЕВЕРНЫЙ ВВОД ДАТЫ!");
        }
        TransmittedActiveServiceParams transmittedActiveServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(id)
                .withDate(newDate)
                .withRequestType("changeNewTariffDate");
        ResponseDTO resp = controller.identifyObject(transmittedActiveServiceParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        response.sendRedirect("/ShowActiveServicesServlet");
    }
}