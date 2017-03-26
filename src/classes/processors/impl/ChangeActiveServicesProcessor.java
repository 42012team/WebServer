package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;

import java.io.Serializable;


public class ChangeActiveServicesProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public ChangeActiveServicesProcessor() {

    }


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
