package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;

@Component("getActiveServiceById")
public class GetActiveServiceByIdProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public GetActiveServiceByIdProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedActiveServiceParams activeServiceParams = (TransmittedActiveServiceParams) request;
            ActiveService activeService = initializer.getActiveServiceManager().getActiveServiceById(activeServiceParams.getId());
            System.out.println("Возврат подключенной услуги с id: " + activeServiceParams.getId());
            return ActiveServiceResponse.create()
                    .withActiveServices(Collections.singletonList(activeService))
                    .withResponseType("activeServiceById");
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
