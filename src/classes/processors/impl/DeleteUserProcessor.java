package classes.processors.impl;

import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;
import classes.response.impl.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("deleteUser")
public class DeleteUserProcessor implements RequestProcessor, Serializable {

    Initializer initializer;

    public DeleteUserProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        TransmittedUserParams userRequestParams = (TransmittedUserParams) request;
        System.out.println("Удаление пользователя с id: " + userRequestParams.getUserId());
        initializer.getUserManager().deleteUser(userRequestParams.getUserId());
        initializer.getActiveServiceManager().deleteActiveServicesByUserId(userRequestParams.getUserId());
        return UserResponse.create().withResponseType("successful");
    }
}
