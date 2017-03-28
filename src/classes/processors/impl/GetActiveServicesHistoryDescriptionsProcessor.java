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

@Component("getHistoryDescriptions")
public class GetActiveServicesHistoryDescriptionsProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public GetActiveServicesHistoryDescriptionsProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedServiceParams serviceRequestParams = (TransmittedServiceParams) request;
            List<ActiveService> activeServicesList = initializer.getActiveServiceManager()
                    .getActiveServicesHistoryByUserId(serviceRequestParams.getUserId(),serviceRequestParams.getServiceId());
            List<Service> activeServicesDescription = initializer.getServiceManager()
                    .getActiveServicesDecriptions(activeServicesList);
            System.out.println("Получение описания истории услуг пользователя с Id "
                    + serviceRequestParams.getUserId());
            return ServiceResponse.create()
                    .withServices(activeServicesDescription)
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
