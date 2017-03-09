package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by User on 09.03.2017.
 */
public class DeleteActiveServiceWithSameTypeByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //   int userId = (int) request.getSession(true).getAttribute("userForChange");
        int userId=Integer.parseInt(request.getParameter("userId"));
        TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(Integer.parseInt(request.getParameter("chooseActiveService")))
                .withUserId(userId)
                .withUnlockingTime((new Date()).getTime() - 3000)
                .withRequestType("deleteTheSameType");
        System.out.println("me be here1!");
        ResponseDTO resp = controller.identifyObject(activeServiceParams);
        request.setAttribute("user_id",userId);
        System.out.println("me be here!");
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        response.sendRedirect("/ChangeUserInfoByAdminServlet?user_id="+userId);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("me be here1111!");
        //   int userId = (int) request.getSession(true).getAttribute("userForChange");
        int userId=Integer.parseInt(request.getParameter("userId"));
        TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(Integer.parseInt(request.getParameter("chooseActiveService")))
                .withUserId(userId)
                .withUnlockingTime((new Date()).getTime() - 3000)
                .withRequestType("deleteTheSameType");
        System.out.println("me be here1!");
        ResponseDTO resp = controller.identifyObject(activeServiceParams);
        request.setAttribute("user_id",userId);
        System.out.println("me be here!");
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        response.sendRedirect("/ChangeUserInfoByAdminServlet?user_id="+userId);
    }

}
