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

public class ChangeUserByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserResponse userResp=(UserResponse)controller.indentifyObject(TransmittedUserParams.create()
                .withId(Integer.parseInt(request.getParameter("chooseUser")))
                .withRequestType("userById"));
        System.out.println(userResp.getUserId()+" "+userResp.getName());
        request.setAttribute("user",userResp);
        request.getRequestDispatcher("/changeUserByAdminPage.jsp").forward(request,response);
    }

}
