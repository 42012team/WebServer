package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.export_import.Import;
import classes.model.Account;
import classes.model.User;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 02.03.2017.
 */
public class ImportServlet  extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Import import_=new Import(request.getParameter("path"));
        List<Account> accountList=import_.getAccountList();
        for(int i=0;i<accountList.size();i++) {
            User user=accountList.get(i).getUser();
            TransmittedUserParams userParams = TransmittedUserParams.create()
                    .withRequestType("mergeUser")
                    .withUser(user)
                    .withUnlockingTime(new Date().getTime() - 3000);
            ResponseDTO resp = controller.identifyObject(userParams);
            if(accountList.get(i).getActiveServices().size()!=0){
            TransmittedActiveServiceParams transmittedActiveServiceParams=TransmittedActiveServiceParams.create()
                    .withActiveServiceList(accountList.get(i).getActiveServices()).withRequestType("storeAllActiveServices");
            controller.identifyObject(transmittedActiveServiceParams);
            }
        }
        response.sendRedirect("/GetAllUsersServlet");

    }
}
