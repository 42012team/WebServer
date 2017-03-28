package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
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
import java.util.List;

@Component("allowedToConnect")
public class GetAllowedToConnectionProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public GetAllowedToConnectionProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            ServiceResponse.create();
            TransmittedServiceParams serviceRequestParams = (TransmittedServiceParams) request;
            List<ActiveService> activeServicesList = initializer.getActiveServiceManager().getActiveServicesByUserId(
                    serviceRequestParams.getUserId());
            System.out.println("Получение подключенных услуг пользователя с Id " + serviceRequestParams.getUserId());
            List<Service> activeServicesDescription = initializer.getServiceManager()
                    .getActiveServicesDecriptions(activeServicesList);
            List<Service> servicesList = initializer.getServiceManager()
                    .getAllowedToConnectServices(activeServicesDescription);
            System.out.println("Получение доступных для подключения услуг пользователя с Id "
                    + serviceRequestParams.getUserId());
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
