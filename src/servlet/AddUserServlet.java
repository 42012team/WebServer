package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddUserServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        TransmittedUserParams userParams = TransmittedUserParams.create()
                .withName(name)
                .withSurname(surname)
                .withEmail(email)
                .withPhone(phone)
                .withAdress(address)
                .withLogin(login)
                .withPassword(password)
                .withRequestType("signInUser")
                .withVersion(0);
        switch (request.getParameter("privilege")) {
            case "admin":
                userParams.withPrivilege("admin");
                break;
            case "user":
                userParams.withPrivilege("user");
                break;
        }
        ResponseDTO resp = controller.identifyObject(userParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        response.sendRedirect("/adminPage.jsp");
    }
}
