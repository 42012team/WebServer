package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.ActiveService;
import classes.model.ActiveServiceParams;
import classes.model.ActiveServiceState;
import classes.model.ActiveServiceStatus;
import classes.model.behavior.managers.ActiveServiceManager;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Component("changeTariffActiveService")
public class ChangeTariffProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public ChangeTariffProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    private ActiveService createNewActiveService(int oldActiveServiceId, int serviceId, int userId, ActiveServiceStatus firstStatus, ActiveServiceStatus secondStatus, Date date, ActiveServiceState state) {
        ActiveServiceManager activeServiceManager = initializer.getActiveServiceManager();
        ActiveServiceParams activeServiceParams = ActiveServiceParams.create()
                .withFirstStatus(firstStatus)
                .withSecondStatus(secondStatus)
                .withServiceId(serviceId)
                .withUserId(userId)
                .withDate(date)
                .withState(state)
                .withOldActiveServiceId(oldActiveServiceId);
        return activeServiceManager.changeTariff(activeServiceParams);
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedActiveServiceParams activeServiceParams = (TransmittedActiveServiceParams) request;

            System.out.println("Добавление новой услуги с Id " + activeServiceParams.getServiceId()
                    + " пользователю с Id " + activeServiceParams.getUserId());
            ActiveService currentActiveService = initializer.getActiveServiceManager()
                    .getActiveServiceById(activeServiceParams.getOldActiveServiceId());
            if (activeServiceParams.getDate().compareTo(currentActiveService.getDate()) <= 0) {
                return TransmittedException.create("Некорректная дата!").withExceptionType("exception");
            }
            ActiveService modifyCurrentActiveService = createNewActiveService(activeServiceParams.getOldActiveServiceId(),
                    currentActiveService.getServiceId(),
                    activeServiceParams.getUserId(), currentActiveService.getSecondStatus(),
                    ActiveServiceStatus.DISCONNECTED, activeServiceParams.getDate(),
                    ActiveServiceState.NOT_READY);
            ActiveService newActiveService = createNewActiveService(modifyCurrentActiveService.getId(),
                    activeServiceParams.getServiceId(),
                    activeServiceParams.getUserId(), ActiveServiceStatus.PLANNED,
                    ActiveServiceStatus.ACTIVE, activeServiceParams.getDate(),
                    ActiveServiceState.NOT_READY);
            if (newActiveService != null) {
                return ActiveServiceResponse.create().withResponseType("activeServices");
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
