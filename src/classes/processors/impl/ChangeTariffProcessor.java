package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.ActiveServiceParams;
import classes.model.ActiveServiceStatus;
import classes.model.behavior.managers.ActiveServiceManager;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by User on 08.03.2017.
 */
public class ChangeTariffProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public ChangeTariffProcessor() {

    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    private boolean createActiveService(int oldActiveServiceId, int serviceId, int userId, ActiveServiceStatus currentStatus, ActiveServiceStatus newStatus, Date date) {
        ActiveServiceManager activeServiceManager = initializer.getActiveServiceManager();
        ActiveServiceParams activeServiceParams = ActiveServiceParams.create()
                .withCurrentStatus(ActiveServiceStatus.PLANNED)
                .withNewStatus(ActiveServiceStatus.ACTIVE)
                .withServiceId(serviceId)
                .withUserId(userId)
                .withDate(date)
                .withOldActiveServiceId(oldActiveServiceId);

        return (activeServiceManager.changeTariff(activeServiceParams) != null);

    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedActiveServiceParams activeServiceParams = (TransmittedActiveServiceParams) request;
            System.out.println("Добавление новой услуги с Id " + activeServiceParams.getServiceId()
                    + " пользователю с Id " + activeServiceParams.getUserId());
            if (createActiveService(activeServiceParams.getOldActiveServiceId(), activeServiceParams.getServiceId(),
                    activeServiceParams.getUserId(), activeServiceParams.getCurrentStatus(),
                    activeServiceParams.getNewStatus(), activeServiceParams.getDate())) {
                return ActiveServiceResponse.create().withResponseType("activeServices")
                        .withActiveServices(initializer.getActiveServiceManager()
                                .getActiveServicesByUserId(activeServiceParams.getUserId()));
            }
        } catch (Exception ex) {
            System.out.println("Exception occured: " + ex.getStackTrace());
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }
        return TransmittedException.create("УСЛУГА ДАННОГО ТИПА УЖЕ ДОБАВЛЕНА!").withExceptionType("exception");
    }
}
