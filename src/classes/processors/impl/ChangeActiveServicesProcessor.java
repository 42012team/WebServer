package classes.processors.impl;

import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;

import java.io.Serializable;

/**
 * Created by User on 02.03.2017.
 */
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
        initializer.getActiveServiceManager().storeActiveServices(transmittedActiveServiceParams.getActiveServicesList());
        return ActiveServiceResponse.create().withResponseType("answer");
    }
}
