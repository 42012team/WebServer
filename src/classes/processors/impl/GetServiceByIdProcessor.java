package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.Service;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;

@Component("getServiceById")
public class GetServiceByIdProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public GetServiceByIdProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedServiceParams serviceParams = (TransmittedServiceParams) request;
            Service service = initializer.getServiceManager().getServiceById(serviceParams.getServiceId());
            System.out.println("Возврат услуги с id: " + serviceParams.getServiceId());
            return ServiceResponse.create()
                    .withServices(Collections.singletonList(service))
                    .withResponseType("serviceById");
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
