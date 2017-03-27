package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ServiceResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetTheSameTypeByCurrentServiceByAdminServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = (Integer.parseInt(request.getParameter("chooseActiveService")));
        request.getSession(true).setAttribute("changedActiveServiceIdByAdmin", id);
        TransmittedActiveServiceParams transmittedActiveServiceParams = TransmittedActiveServiceParams.create()
                .withActiveServiceId(id)
                .withRequestType("theSameType");

        ResponseDTO resp = controller.identifyObject(transmittedActiveServiceParams);
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ServiceResponse serviceResponse=(ServiceResponse)resp;
        request.setAttribute("theSameTypeWithCurrentActiveService",serviceResponse.getServices());
        request.getRequestDispatcher("/tariffChangeByAdminPage.jsp").forward(request,response);
    }
}
