package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;

import java.io.Serializable;

public class GetActiveServicesHistoryProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public GetActiveServicesHistoryProcessor() {

    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedActiveServiceParams activeServicesRequestParams
                    = (TransmittedActiveServiceParams) request;
            System.out.println("Получение истории услуг пользователя с Id "
                    + activeServicesRequestParams.getUserId());
            return ActiveServiceResponse.create().withActiveServices(initializer.getActiveServiceManager()
                    .getActiveServicesHistoryByUserId(activeServicesRequestParams.getUserId(),activeServicesRequestParams.getServiceId()))
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
