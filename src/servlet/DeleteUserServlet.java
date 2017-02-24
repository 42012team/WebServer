package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.request.impl.TransmittedUserParams;
import classes.response.impl.UserResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 24.02.2017.
 */
public class DeleteUserServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       int id=18;//а вообще этот Id надо тебе будет с твой стр всех users получить,Данил))
        UserResponse userResponse= (UserResponse) controller.indentifyObject(TransmittedUserParams.create()
                .withRequestType("deleteUser")
                 .withId(id));
        response.sendRedirect("/GetAllUsersServlet");

    }
}
