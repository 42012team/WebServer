package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.model.User;
import classes.request.impl.TransmittedUserParams;
import classes.response.impl.UserResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 08.02.2017.
 */
public class SignInServlet extends HttpServlet {
    WebController controller=null;
    @Override
    public void init() throws ServletException {
        controller= Initialization.getInstance().initialization();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        User user=new User(userResponse.getUserId(),userResponse.getName(),userResponse.getSurname(),userResponse.getEmail(),
                userResponse.getPhone(),userResponse.getAddress(),userResponse.getLogin(),userResponse.getPassword(),userResponse.getVersion(),
                userResponse.getPrivilege());
        request.getSession(true).setAttribute("user",user);
        setProfileAttribute(user,request,response);
        request.getRequestDispatcher("profilePage.jsp").forward(request, response);
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
