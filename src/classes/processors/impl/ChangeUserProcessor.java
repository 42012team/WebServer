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

@Component("changeUser")
public class ChangeUserProcessor implements RequestProcessor, Serializable {

    Initializer initializer;

    public ChangeUserProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    private User changeUser(int id, String name, String surname, String email, String phone, String address,
                            String login, String password, int version, String privilege) {
        UserManager userManager = initializer.getUserManager();
        UserParams userParams = UserParams.create()
                .withName(name)
                .withSurname(surname)
                .withEmail(email)
                .withPhone(phone)
                .withAdress(address)
                .withLogin(login)
                .withPassword(password)
                .withVersion(version + 1)
                .withPrivilege(privilege);
        User user = userManager.getUserById(id);
        userManager.changeUser(user, userParams);
        return user;
    }

    public UserResponse getResponse(TransmittedUserParams userRequestParams) {
        User user = changeUser(userRequestParams.getUserId(), userRequestParams.getName(), userRequestParams.getSurname(), userRequestParams.getEmail(), userRequestParams.getPhone(),
                userRequestParams.getAddress(), userRequestParams.getLogin(), userRequestParams.getPassword(), userRequestParams.getVersion(), userRequestParams.getPrivilege());
        System.out.println("Изменение пользователя с Id " + user.getId());
        return UserResponse.create()
                .withResponseType("user");
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedUserParams userRequestParams = (TransmittedUserParams) request;
            if (initializer.getLockingManager().isAvailableUser(userRequestParams)) {
                return getResponse(userRequestParams);
            } else {
                return TransmittedException.create("НЕВОЗМОЖНО ИЗМЕНИТЬ ДАННЫЕ!").withExceptionType("exception");
            }

        } catch (Exception ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }
    }

}