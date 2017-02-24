package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by User on 20.02.2017.
 */
public class BackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).removeAttribute("previousPage");
        List<String> linkedList = (List<String>) request.getSession(true).getAttribute("back");
        String link = linkedList.get(linkedList.size() - 1);
        System.out.println(link + "list");
        linkedList.remove(linkedList.size() - 1);
        request.getSession(true).removeAttribute("back");
        request.getSession(true).setAttribute("back", linkedList);
      //  request.getRequestDispatcher(link).forward(request, response);
        response.sendRedirect(link);
    }
}
