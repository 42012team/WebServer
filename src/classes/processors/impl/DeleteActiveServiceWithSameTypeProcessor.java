package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.behavior.managers.ActiveServiceManager;
import classes.pessimisticLock.PessimisticLockingThread;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ActiveServiceResponse;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by User on 09.03.2017.
 */
public class DeleteActiveServiceWithSameTypeProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public DeleteActiveServiceWithSameTypeProcessor() {

    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }
//deleteTheSameType
    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedActiveServiceParams activeServiceParams = (TransmittedActiveServiceParams) request;
            if (initializer.getTypeOfLock().equals("optimistic")) {
                if (initializer.getActiveServiceManager()
                        .getActiveServiceById(activeServiceParams.getId()) != null) {
                    ActiveServiceManager activeServiceManager = initializer.getActiveServiceManager();
                    activeServiceManager.deleteActiveServicesWithTheSameType(activeServiceParams.getId());
                    return ActiveServiceResponse.create().withResponseType("activeServices").withActiveServices(initializer.getActiveServiceManager().getActiveServicesByUserId(activeServiceParams.getUserId()));
                }
            } else {
                ActiveServiceManager activeServiceManager = initializer.getActiveServiceManager();
                if (activeServiceParams.getUnlockingTime() > new Date().getTime()) {
                    activeServiceManager.deleteActiveService(activeServiceParams.getId());
                    PessimisticLockingThread.unschedule(activeServiceParams.getId());
                    return ActiveServiceResponse.create().withResponseType("activeServices").withActiveServices(initializer.getActiveServiceManager().getActiveServicesByUserId(activeServiceParams.getUserId()));
                } else {
                    return TransmittedException.create("УДАЛЕНИЕ НЕВОЗМОЖНО! ИСТЕКЛО ВРЕМЯ ОЖИДАНИЯ ЗАПРОСА!").withExceptionType("exception");
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }
        return TransmittedException.create("ПРОИЗОШЛА ОШИБКА!").withExceptionType("exception");
    }
}
