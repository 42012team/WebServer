package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("storeAllActiveServices")
public class ChangeActiveServicesProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public ChangeActiveServicesProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        TransmittedActiveServiceParams transmittedActiveServiceParams=(TransmittedActiveServiceParams)request;
        try {
            initializer.getActiveServiceManager().storeActiveServices(transmittedActiveServiceParams.getActiveServicesList());
        } catch(Exception ex){
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }
        return ActiveServiceResponse.create().withResponseType("answer");
    }
}
