package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.ServiceParams;
import classes.model.ServiceStatus;
import classes.model.behavior.managers.ServiceManager;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("createService")
public class CreateServiceProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public CreateServiceProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    private boolean createService(String name, String description, String type, ServiceStatus serviceStatus) {
        ServiceManager serviceManager = initializer.getServiceManager();
        ServiceParams serviceParams = ServiceParams.create()
                .withName(name)
                .withDescription(description)
                .withType(type)
                .withStatus(serviceStatus);
        return (serviceManager.createService(serviceParams) != null);

    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedServiceParams serviceParams = (TransmittedServiceParams) request;
            if (createService(serviceParams.getName(), serviceParams.getDescription(), serviceParams.getServiceType(),
                    serviceParams.getServiceStatus())) {
                return ServiceResponse.create().withResponseType("services");
            }
        }catch (Exception ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }
        return TransmittedException.create("УСЛУГА ДАННОГО ТИПА УЖЕ ДОБАВЛЕНА!").withExceptionType("exception");
    }

}
