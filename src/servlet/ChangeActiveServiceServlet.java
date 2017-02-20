package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 10.02.2017.
 */
public class ChangeActiveServiceServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("chooseActiveService"));
        String info = request.getParameter(Integer.toString(id));
        request.setAttribute("activeService", info);
        request.getRequestDispatcher("changeActiveServiceServlet.jsp").forward(request, response);
    }
}
