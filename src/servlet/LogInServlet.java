package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.model.User;
import classes.request.impl.TransmittedUserParams;
import classes.response.impl.UserResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogInServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TransmittedUserParams transmittedUserParams = TransmittedUserParams.create()
                .withLogin(request.getParameter("login"))
                .withPassword(request.getParameter("pass"))
                .withRequestType("logIn");
        UserResponse userResponse = (UserResponse) controller.indentifyObject(transmittedUserParams);
        User user = new User(userResponse.getUserId(), userResponse.getName(), userResponse.getSurname(), userResponse.getEmail(),
                userResponse.getPhone(), userResponse.getAddress(), userResponse.getLogin(), userResponse.getPassword(), userResponse.getVersion(),
                userResponse.getPrivilege());
        request.getSession(true).setAttribute("user", user);
        switch (userResponse.getPrivilege()){
            case "user":
                response.sendRedirect("profilePage.jsp");
                break;
            case "admin":
                response.sendRedirect("adminPage.jsp");
                break;
        }
    }

}
