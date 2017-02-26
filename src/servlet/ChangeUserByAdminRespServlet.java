package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.request.impl.TransmittedUserParams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ChangeUserByAdminRespServlet extends HttpServlet {
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
        String password = request.getParameter("password");
        TransmittedUserParams userParams = TransmittedUserParams.create()
                .withRequestType("changeUser")
                .withName(name)
                .withSurname(surname)
                .withEmail(email)
                .withPhone(phone)
                .withAdress(address)
                .withPassword(password)
                .withId(Integer.parseInt(request.getParameter("userId")))
                .withVersion(Integer.parseInt(request.getParameter("version")))
                .withLogin(request.getParameter("login"))
                .withUnlockingTime(new Date().getTime() - 3000);
        switch (request.getParameter("privilege")){
            case "admin":
                userParams.withPrivilege("admin");
                controller.indentifyObject(userParams);
                break;
            case "user":
                userParams.withPrivilege("user");
                controller.indentifyObject(userParams);
                break;
        }
        response.sendRedirect("/GetAllUsersServlet");
    }
}