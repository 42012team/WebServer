package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

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
            System.out.println("blabla");
            return ActiveServiceResponse.create().withResponseType("activeServices");
        } catch (Exception ex) {
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }

    }
}