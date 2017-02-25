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

public class GetAllUsersServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserResponse userResponse= (UserResponse) controller.indentifyObject(TransmittedUserParams.create()
                .withRequestType("allUsers"));
        request.setAttribute("allUsers",userResponse.getAllUsers());
        request.getRequestDispatcher("/allUsers.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserResponse userResponse = (UserResponse) controller.indentifyObject(TransmittedUserParams.create()
                .withRequestType("allUsers"));
        request.setAttribute("allUsers",userResponse.getAllUsers());
        request.getRequestDispatcher("/allUsers.jsp").forward(request,response);
    }
}
