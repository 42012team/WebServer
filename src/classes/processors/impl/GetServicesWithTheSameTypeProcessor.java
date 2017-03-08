package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.Service;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ServiceResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 06.03.2017.
 */
public class GetServicesWithTheSameTypeProcessor  implements RequestProcessor, Serializable {

    private Initializer initializer;

    public GetServicesWithTheSameTypeProcessor() {

    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            ServiceResponse.create();
            TransmittedActiveServiceParams activeServiceParams = (TransmittedActiveServiceParams) request;
            List<Service> servicesList = initializer.getServiceManager().getServicesBySameType(
                    activeServiceParams.getId());
            System.out.println(servicesList.size());
            return ServiceResponse.create()
                    .withServices(servicesList)
                    .withResponseType("services");
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
