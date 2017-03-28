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

@Component("allActiveServices")
public class GetAllActiveServicesProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public GetAllActiveServicesProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedActiveServiceParams activeServicesRequestParams
                    = (TransmittedActiveServiceParams) request;
            System.out.println("Получение подключенных услуг пользователя с Id "
                    + activeServicesRequestParams.getUserId());
            return ActiveServiceResponse.create().withActiveServices(initializer.getActiveServiceManager()
                    .getActiveServicesByUserId(activeServicesRequestParams.getUserId()))
                    .withResponseType("activeServices");
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
