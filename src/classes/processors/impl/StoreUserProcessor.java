package classes.processors.impl;

import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedUserParams;
import classes.response.ResponseDTO;
import classes.response.impl.UserResponse;

import java.io.Serializable;

/**
 * Created by User on 02.03.2017.
 */
public class StoreUserProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public StoreUserProcessor() {

    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        TransmittedUserParams transmittedUserParams=(TransmittedUserParams)request;
      //  initializer.getUserManager().
        return UserResponse.create();
    }
}
