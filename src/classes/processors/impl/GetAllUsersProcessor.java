package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.User;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.response.ResponseDTO;
import classes.response.impl.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component("allUsers")
public class GetAllUsersProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public GetAllUsersProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            List<User> usersList = initializer.getUserManager().getAllUsers();
            System.out.println("Возврат всех пользователей");
            return UserResponse.create()
                    .withAllUsers(usersList)
                    .withResponseType("allUsers");
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
