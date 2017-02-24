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
import java.util.List;

public class ShowProfilePageServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession(true).getAttribute("user");
        TransmittedUserParams transmittedUserParams = TransmittedUserParams.create()
                .withLogin(currentUser.getLogin())
                .withPassword(currentUser.getPassword())
                .withRequestType("logIn");
        UserResponse userResponse = (UserResponse) controller.indentifyObject(transmittedUserParams);
        User user = new User(userResponse.getUserId(), userResponse.getName(), userResponse.getSurname(), userResponse.getEmail(),
                userResponse.getPhone(), userResponse.getAddress(), userResponse.getLogin(), userResponse.getPassword(), userResponse.getVersion(),
                userResponse.getPrivilege());
        request.getSession(true).setAttribute("user", user);
        request.getRequestDispatcher("profilePage.jsp").forward(request, response);
    }
}
