package servlet;

import classes.activator.Activator;
import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.idgenerator.IdGenerator;
import classes.idgenerator.IdGeneratorSingletonDB;
import classes.model.ActiveService;
import classes.model.Service;
import classes.model.User;
import classes.model.behavior.managers.ActiveServiceManager;
import classes.model.behavior.managers.ServiceManager;
import classes.model.behavior.managers.UserManager;
import classes.model.behavior.storages.impl.*;
import classes.processors.Configuration;
import classes.processors.ConfigurationXML;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedServiceParams;
import classes.request.impl.TransmittedUserParams;
import classes.response.impl.ActiveServiceResponse;
import classes.response.impl.ServiceResponse;
import classes.response.impl.UserResponse;
import jdk.nashorn.internal.ir.RuntimeNode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 05.02.2017.
 */
public class LogInServlet extends HttpServlet {
//    User user=null;
 /*   IdGenerator idGenerator = null;
    UserManager userManager = null;
  ServiceManager serviceManager =null;
    ActiveServiceManager activeServiceManager = null;
    Initializer initializer=null;
    WebController controller=null;
    User user=null;*/
 WebController controller=null;
     @Override
    public void init() throws ServletException {
         controller= Initialization.getInstance().initialization();
     }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

   //     if (request.getParameter("loginButton")!= null) {
            TransmittedUserParams transmittedUserParams=TransmittedUserParams.create()
                    .withLogin(request.getParameter("login"))
                    .withPassword(request.getParameter("pass"))
                    .withRequestType("logIn");
            UserResponse userResponse= (UserResponse) controller.indentifyObject(transmittedUserParams);
         User user=new User(userResponse.getUserId(),userResponse.getName(),userResponse.getSurname(),userResponse.getEmail(),
                    userResponse.getPhone(),userResponse.getAddress(),userResponse.getLogin(),userResponse.getPassword(),userResponse.getVersion(),
                    userResponse.getPrivilege());
            request.getSession(true).setAttribute("user",user);
            setProfileAttribute(user,request,response);
            request.getRequestDispatcher("profilePage.jsp").forward(request, response);
       // }

    /*    if(request.getParameter("signInButton")!=null){
            String name=request.getParameter("name");
            String surname=request.getParameter("surname");
            String email=request.getParameter("email");
            String phone=request.getParameter("phone");
            String address=request.getParameter("address");
            String login=request.getParameter("login");
            String password=request.getParameter("password");
            System.out.println(name+" "+login);
            TransmittedUserParams userParams = TransmittedUserParams.create()
                    .withName(name)
                    .withSurname(surname)
                    .withEmail(email)
                    .withPhone(phone)
                    .withAdress(address)
                    .withLogin(login)
                    .withPassword(password)
                    .withRequestType("signInUser")
                    .withVersion(0)
                    .withPrivilege("user");
            controller.indentifyObject(userParams);
            TransmittedUserParams transmittedUserParams1=TransmittedUserParams.create()
                    .withLogin(login)
                    .withPassword(password)
                    .withRequestType("logIn");
            UserResponse userResponse= (UserResponse) controller.indentifyObject(transmittedUserParams1);
       User   user=new User(userResponse.getUserId(),userResponse.getName(),userResponse.getSurname(),userResponse.getEmail(),
                    userResponse.getPhone(),userResponse.getAddress(),userResponse.getLogin(),userResponse.getPassword(),userResponse.getVersion(),
                    userResponse.getPrivilege());
            request.getSession(true).setAttribute("user",user);
            setProfileAttribute(user,request,response);
            request.getRequestDispatcher("profilePage.jsp").forward(request, response);
        }
        if(request.getParameter("changeProfileButton")!=null){
            request.getRequestDispatcher("changeProfilePage.jsp").forward(request, response);
        }
        if(request.getParameter("saveChangesButton")!=null){
          User user=(User)request.getSession(true).getAttribute("user");
            String name=request.getParameter("name");
            String surname=request.getParameter("surname");
            String email=request.getParameter("email");
            String phone=request.getParameter("phone");
            String address=request.getParameter("address");
            String password=request.getParameter("password");
            TransmittedUserParams userParams = TransmittedUserParams.create()
                    .withRequestType("changeUser")
                    .withName(name)
                    .withSurname(surname)
                    .withEmail(email)
                    .withPhone(phone)
                    .withAdress(address)
                    .withLogin(user.getLogin())
                    .withPassword(password)
                    .withId(user.getId())
                    .withVersion(user.getVersion())
                    .withPrivilege(user.getPrivilege())
                    .withUnlockingTime(new Date().getTime()-3000);
            UserResponse userResponse= (UserResponse) controller.indentifyObject(userParams);
            user=new User(userResponse.getUserId(),userResponse.getName(),userResponse.getSurname(),userResponse.getEmail(),
                    userResponse.getPhone(),userResponse.getAddress(),userResponse.getLogin(),userResponse.getPassword(),userResponse.getVersion(),
                    userResponse.getPrivilege());
            setProfileAttribute(user,request,response);
            request.getSession(true).removeAttribute("user");
            request.getSession(true).setAttribute("user",user);
            request.getRequestDispatcher("profilePage.jsp").forward(request, response);

        }
        if(request.getParameter("link")!=null){
            User user= (User) request.getSession(true).getAttribute("user");
            ActiveServiceResponse activeServiceResponse=(ActiveServiceResponse)
                    controller.indentifyObject(TransmittedActiveServiceParams.create().
                            withUserId(user.getId()).withRequestType("allActiveServices"));
            List<ActiveService> activeServicesList=activeServiceResponse.getAllActiveServices();
            System.out.println(activeServicesList.size()+"acsl");
            ServiceResponse serviceResponse= (ServiceResponse) controller.indentifyObject(TransmittedServiceParams.create().
                    withUserId(user.getId()).withRequestType("activeServicesDescriptions"));
            List<Service> serviceList=serviceResponse.getServices();
            request.setAttribute("activeServiceDescription",serviceList);
            request.setAttribute("activeServiceList",activeServicesList);
            request.getRequestDispatcher("showAllActiveService.jsp").forward(request, response);
        }*/

      /*  if(request.getParameter("saveChangesButton")!=null){
            String name=request.getParameter("name");
            String surname=request.getParameter("surname");
            String email=request.getParameter("email");
            String phone=request.getParameter("phone");
            String address=request.getParameter("address");
            String password=request.getParameter("password");
            TransmittedUserParams userParams = TransmittedUserParams.create()
                    .withRequestType("changeUser")
                    .withName(name)
                    .withSurname(surname)
                    .withEmail(email)
                    .withPhone(phone)
                    .withAdress(address)
                    .withLogin(user.getLogin())
                    .withPassword(password)
                    .withId(user.getId())
                    .withVersion(user.getVersion())
                    .withPrivilege(user.getPrivilege())
                    .withUnlockingTime(new Date().getTime());
            UserResponse userResponse= (UserResponse) controller.indentifyObject(userParams);
            user=new User(userResponse.getUserId(),userResponse.getName(),userResponse.getSurname(),userResponse.getEmail(),
                    userResponse.getPhone(),userResponse.getAddress(),userResponse.getLogin(),userResponse.getPassword(),userResponse.getVersion(),
                    userResponse.getPrivilege());
            setProfileAttribute(user,request,response);
            request.getRequestDispatcher("profilePage.jsp").forward(request, response);
        }*/
    }
    private void setProfileAttribute(User user, HttpServletRequest request,HttpServletResponse response){
        request.setAttribute("name",user.getName());
        request.setAttribute("surname",user.getSurname());
        request.setAttribute("email",user.getEmail());
        request.setAttribute("phone",user.getPhone());
        request.setAttribute("address",user.getAddress());
        request.setAttribute("login",user.getLogin());
        request.setAttribute("password",user.getPassword());
    }
}
