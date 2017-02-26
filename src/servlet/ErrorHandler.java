package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws IOException, ServletException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }
        request.setAttribute("requestUri", requestUri);
        request.setAttribute("message", (throwable.getMessage() == null) ? "ОШИБКА 404!" : throwable.getMessage());
        request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
    }
}