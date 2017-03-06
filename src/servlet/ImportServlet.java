/*package servlet;

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
*/
package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.export_import.Import;
import classes.model.Account;
import classes.model.User;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Created by User on 05.03.2017.
 */

@MultipartConfig(fileSizeThreshold=1024*1024*2,
        maxFileSize=1024*1024*10,
        maxRequestSize=1024*1024*50)

public class ImportServlet extends HttpServlet {
    WebController controller = null;
    private static final String SAVE_DIR = "uploadFiles";
    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String appPath = request.getServletContext().getRealPath("");
        String savePath = "D:" + File.separator + "nc"+ File.separator + "import";
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        String filePath=null;
        for (Part part : request.getParts()) {
            String fileName = extractFileName(part);
            fileName = new File(fileName).getName();
            filePath=savePath + File.separator + fileName;
            part.write(savePath + File.separator + fileName);
        }

        Import import_=new Import(filePath);
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

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
