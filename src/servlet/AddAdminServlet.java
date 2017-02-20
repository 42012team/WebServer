package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.request.impl.TransmittedUserParams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        TransmittedUserParams userParams = TransmittedUserParams.create()
                .withName(" ")
                .withSurname(" ")
                .withEmail(" ")
                .withPhone(" ")
                .withAdress(" ")
                .withLogin(login)
                .withPassword(password)
                .withVersion(0)
                .withPrivilege("admin")
                .withRequestType("signInUser");
        controller.indentifyObject(userParams);
        response.sendRedirect("/adminPage.jsp");
    }
}
