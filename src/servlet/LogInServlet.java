package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.User;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;
import classes.response.impl.UserResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        ResponseDTO resp = controller.identifyObject(transmittedUserParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        UserResponse userResponse = (UserResponse) resp;
        User user = new User(userResponse.getUserId(), userResponse.getName(), userResponse.getSurname(), userResponse.getEmail(),
                userResponse.getPhone(), userResponse.getAddress(), userResponse.getLogin(), userResponse.getPassword(), userResponse.getVersion(),
                userResponse.getPrivilege());
        request.getSession(true).setAttribute("user", user);
        List<String> linksList = new ArrayList<String>();
        request.getSession(true).setAttribute("back", linksList);
        switch (userResponse.getPrivilege()) {
            case "user":
                response.sendRedirect("/profilePage.jsp");
                break;
            case "admin":
                response.sendRedirect("/adminPage.jsp");
                break;
        }
    }

}
