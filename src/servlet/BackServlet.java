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
     List<String> linkedList= (List<String>) request.getSession(true).getAttribute("back");
        System.out.println(linkedList.size()+"lalala");
        String list=linkedList.get(linkedList.size()-1);
        System.out.println(list+"list");
        System.out.println(linkedList.get(0));
        linkedList.remove(linkedList.size()-1);
        System.out.println(linkedList.size());
        request.getSession(true).removeAttribute("back");
        request.getSession(true).setAttribute("back",linkedList);
        request.getRequestDispatcher(list).forward(request, response);

    }
}
