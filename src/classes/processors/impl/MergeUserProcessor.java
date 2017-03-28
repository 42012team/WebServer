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

@Component("mergeUser")
public class MergeUserProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public MergeUserProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        TransmittedUserParams transmittedUserParams=(TransmittedUserParams)request;
        initializer.getUserManager().mergeUser(transmittedUserParams.getUser());
        return UserResponse.create();
    }
}
