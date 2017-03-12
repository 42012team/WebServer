package servlet;
import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.export_import.Export;
import classes.model.Account;
import classes.model.ActiveService;
import classes.model.User;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;
import classes.response.impl.UserResponse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

public class ExportServlet extends HttpServlet {
    private long i=2;
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Account> accountsList=new ArrayList<Account>();
        String[] checkedUserId=request.getParameterValues("chooseUser");
        for (int i = 0; i < checkedUserId.length; i++) {
            int id=Integer.parseInt(checkedUserId[i]);
            Account account=new Account(getUserById(id));
            account.setActiveServices(getActiveServicesById(id));
            accountsList.add(account);
        }
        Export export=new Export("D:\\nc\\export"+i+".xml");
        export.storeAccount(accountsList);
        String filePath = "D:\\nc";
        String fileName ="export"+i+".xml";
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream out=response.getOutputStream();
        response.addHeader("Content-Disposition","attachment;filename="+fileName);
        File f = new File("D:\\nc\\export"+i+".xml");
        long len = f.length();
        response.addHeader("Content-Length", String.valueOf(len));
        response.setContentType("application/download");
        FileInputStream fileInputStream = new FileInputStream(f);
        int i;
        while((i=fileInputStream.read())!=-1){
            out.write(i);
        }
        fileInputStream.close();
        out.close();
        i++;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        response.sendRedirect("/GetAllUsersServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        response.sendRedirect("/GetAllUsersServlet");
    }

    private User getUserById(int id) throws ServletException {
        ResponseDTO resp = controller.identifyObject(TransmittedUserParams.create()
                .withId(id)
                .withRequestType("userById"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        UserResponse userResp = (UserResponse) resp;
        UserResponse userResponse = (UserResponse) resp;
        User user = new User(userResponse.getUserId(), userResponse.getName(), userResponse.getSurname(), userResponse.getEmail(),
                userResponse.getPhone(), userResponse.getAddress(), userResponse.getLogin(), userResponse.getPassword(), userResponse.getVersion(),
                userResponse.getPrivilege());
        return user;
    }

    private List<ActiveService> getActiveServicesById(int id) throws ServletException {
        ResponseDTO resp = controller.identifyObject(TransmittedActiveServiceParams.create().
                withUserId(id).withRequestType("allActiveServices"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        return activeServicesList;
    }

}

/*import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.export_import.Export;
import classes.model.Account;
import classes.model.ActiveService;
import classes.model.User;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;
import classes.response.impl.UserResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExportServlet extends HttpServlet {
    WebController controller = null;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Account> accountsList=new ArrayList<Account>();
        String[] checkedUserId=request.getParameterValues("chooseUser");
        for (int i = 0; i < checkedUserId.length; i++) {
            int id=Integer.parseInt(checkedUserId[i]);
           Account account=new Account(getUserById(id));
            account.setActiveServices(getActiveServicesById(id));
            accountsList.add(account);
        }
        System.out.println("go in export");
        System.out.println(request.getParameter("filename")+"filename");
        Export export=new Export("");
        export.storeAccount(accountsList);
        response.sendRedirect("/GetAllUsersServlet");


    }
    private User getUserById(int id) throws ServletException {
        ResponseDTO resp = controller.identifyObject(TransmittedUserParams.create()
                .withId(id)
                .withRequestType("userById"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        UserResponse userResp = (UserResponse) resp;
        UserResponse userResponse = (UserResponse) resp;
        User user = new User(userResponse.getUserId(), userResponse.getName(), userResponse.getSurname(), userResponse.getEmail(),
                userResponse.getPhone(), userResponse.getAddress(), userResponse.getLogin(), userResponse.getPassword(), userResponse.getVersion(),
                userResponse.getPrivilege());
        return user;
    }

    private List<ActiveService> getActiveServicesById(int id) throws ServletException {
        ResponseDTO resp = controller.identifyObject(TransmittedActiveServiceParams.create().
                withUserId(id).withRequestType("allActiveServices"));
        if (resp.getResponseType().equals("exception"))
            throw new ServletException(((TransmittedException) resp).getMessage());
        ActiveServiceResponse activeServiceResponse = (ActiveServiceResponse) resp;
        List<ActiveService> activeServicesList = activeServiceResponse.getAllActiveServices();
        return activeServicesList;
    }
}
*/

