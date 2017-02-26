package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BackServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession(true).removeAttribute("previousPage");
        List<String> linkedList = (List<String>) request.getSession(true).getAttribute("back");
        String link = linkedList.get(linkedList.size() - 1);
        linkedList.remove(linkedList.size() - 1);
        request.getSession(true).removeAttribute("back");
        request.getSession(true).setAttribute("back", linkedList);
        response.sendRedirect(link);
    }
}
