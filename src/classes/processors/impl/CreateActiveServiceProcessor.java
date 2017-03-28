package classes.processors.impl;

import classes.exceptions.TransmittedException;
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

@Component("createActiveService")
public class CreateActiveServiceProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public CreateActiveServiceProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    private boolean createActiveService(int id, int serviceId, int userId, ActiveServiceStatus currentStatus, ActiveServiceStatus newStatus, Date date,ActiveServiceState state) {
        ActiveServiceManager activeServiceManager = initializer.getActiveServiceManager();
        ActiveServiceParams activeServiceParams = ActiveServiceParams.create()
                .withFirstStatus(ActiveServiceStatus.PLANNED)
                .withSecondStatus(ActiveServiceStatus.ACTIVE)
                .withState(state)
                .withServiceId(serviceId)
                .withUserId(userId)
                .withDate(date);
        return (activeServiceManager.createActiveService(activeServiceParams) != null);

    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedActiveServiceParams activeServiceParams = (TransmittedActiveServiceParams) request;
            System.out.println("Добавление новой услуги с Id " + activeServiceParams.getServiceId()
                    + " пользователю с Id " + activeServiceParams.getUserId());
            if (createActiveService(activeServiceParams.getId(), activeServiceParams.getServiceId(),
                    activeServiceParams.getUserId(), activeServiceParams.getFirstStatus(),
                    activeServiceParams.getSecondStatus(), activeServiceParams.getDate(),activeServiceParams.getState())) {
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
