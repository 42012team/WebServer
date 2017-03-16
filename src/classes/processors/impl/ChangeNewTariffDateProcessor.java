package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;

import java.io.Serializable;

public class ChangeNewTariffDateProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public ChangeNewTariffDateProcessor() {

    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }


    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedActiveServiceParams activeServiceParams = (TransmittedActiveServiceParams) request;
            System.out.println("Изменение даты смены тарифа у подключенной услуги с Id " + activeServiceParams.getId());
            initializer.getActiveServiceManager().changeNewTariffDate(activeServiceParams.getId(), activeServiceParams.getDate());
            return ActiveServiceResponse.create().withResponseType("activeServices").
                    withActiveServices(initializer.getActiveServiceManager()
                            .getActiveServicesByUserId(activeServiceParams.getUserId()));
        } catch (Exception ex) {
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }

    }
}