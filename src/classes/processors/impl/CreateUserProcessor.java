package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.User;
import classes.model.UserParams;
import classes.model.behavior.managers.UserManager;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;
import classes.response.impl.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("signInUser")
public class CreateUserProcessor implements RequestProcessor, Serializable {

    Initializer initializer;

    public CreateUserProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    private User signIn(String name, String surname, String email, String phone, String address, String login,
                        String password, int version, String privilege) {
        UserManager userManager = initializer.getUserManager();
        UserParams userParams = UserParams.create()
                .withName(name)
                .withSurname(surname)
                .withEmail(email)
                .withPhone(phone)
                .withAdress(address)
                .withLogin(login)
                .withPassword(password)
                .withVersion(version)
                .withPrivilege(privilege);
        User user = userManager.createUser(userParams);
        return user;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedUserParams userRequestParams = (TransmittedUserParams) request;
            System.out.println("Создание пользователя с параметрами " + "name:" + userRequestParams.getName()
                    + " surname:" + userRequestParams.getSurname() + " email:" + userRequestParams.getEmail()
                    + " phone:" + userRequestParams.getPhone() + " address:" + userRequestParams.getAddress()
                    + " login:" + userRequestParams.getLogin() + " password:" + userRequestParams.getPassword()
                    +" privilege:"+ userRequestParams.getPrivilege());
            if (initializer.getUserManager().getUserByLogin(userRequestParams.getLogin()) == null) {
                User user = signIn(userRequestParams.getName(), userRequestParams.getSurname(), userRequestParams.getEmail(), userRequestParams.getPhone(), userRequestParams.getAddress(),
                        userRequestParams.getLogin(), userRequestParams.getPassword(), userRequestParams.getVersion(), userRequestParams.getPrivilege());
                System.out.println("Присвоенный Id:" + user.getId());
                return UserResponse.create()
                        .withResponseType("user");
            }
        } catch (Exception ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }
        return TransmittedException.create("ПОЛЬЗОВАТЕЛЬ С ТАКИМ ЛОГИНОМ УЖЕ СУЩЕСТВУЕТ!").withExceptionType("exception");
    }

}
