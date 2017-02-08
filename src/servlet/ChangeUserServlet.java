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
import java.util.Date;

/**
 * Created by User on 08.02.2017.
 */
public class ChangeUserServlet extends HttpServlet {
    WebController controller=null;
    @Override
    public void init() throws ServletException {
        controller= Initialization.getInstance().initialization();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
