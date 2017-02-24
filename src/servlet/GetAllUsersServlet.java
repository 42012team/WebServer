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
 * Created by User on 23.02.2017.
 */
public class GetAllUsersServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserResponse userResponse= (UserResponse) controller.indentifyObject(TransmittedUserParams.create().withRequestType("allUsers"));
        System.out.println(userResponse.getAllUsers().size());
        response.sendRedirect("startPage.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      UserResponse userResponse= (UserResponse) controller.indentifyObject(TransmittedUserParams.create().withRequestType("allUsers"));
       System.out.println(userResponse.getAllUsers().size());
        response.sendRedirect("startPage.jsp");
    }
}
