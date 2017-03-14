package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.ActiveServiceParams;
import classes.model.ActiveServiceState;
import classes.model.ActiveServiceStatus;
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

public class ChangeActiveServiceProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public ChangeActiveServiceProcessor() {

    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    private void changeActiveService(int id, int serviceId, int userId, ActiveServiceStatus currentStatus,
                ActiveServiceStatus newStatus, Date date, int version, ActiveServiceState state,int nextActiveServiceId) throws Exception {
        ActiveServiceManager activeServiceManager = initializer.getActiveServiceManager();
        ActiveServiceParams activeServiceParams = ActiveServiceParams.create()
                .withFirstStatus(currentStatus)
                .withSecondStatus(newStatus)
                .withServiceId(serviceId)
                .withUserId(userId)
                .withDate(date)
                .withVersion(version)
                .withNextActiveServiceId(nextActiveServiceId)
                .withState(state);
        activeServiceManager.changeActiveService(activeServiceManager.getActiveServiceById(id), activeServiceParams);

    }

    private ResponseDTO getResponse(TransmittedActiveServiceParams activeServiceParam) {
        try {

            changeActiveService(activeServiceParam.getId(), activeServiceParam.getServiceId(),
                    activeServiceParam.getUserId(), activeServiceParam.getFirstStatus(),
                    activeServiceParam.getSecondStatus(), activeServiceParam.getDate(), initializer.getActiveServiceManager()
                            .getActiveServiceById(activeServiceParam.getId()).getVersion(),activeServiceParam.getState(),activeServiceParam.getNextActiveServiceId());
            System.out.println("Изменение подключенной услуги с Id " + activeServiceParam.getId());
            return ActiveServiceResponse.create().withResponseType("activeServices").
                    withActiveServices(initializer.getActiveServiceManager()
                            .getActiveServicesByUserId(activeServiceParam.getUserId()));
        } catch (Exception ex) {
            return TransmittedException.create("ОШИБКА 404!").withExceptionType("exception");
        }
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedActiveServiceParams activeServiceParams = (TransmittedActiveServiceParams) request;
            if (initializer.getTypeOfLock().equals("optimistic")) {
                if ((initializer.getActiveServiceManager()
                        .getActiveServiceById(activeServiceParams.getId()) != null)
                        && (initializer.getActiveServiceManager().getActiveServiceById(activeServiceParams.getId())
                        .getVersion() == activeServiceParams.getVersion())) {
                    return getResponse(activeServiceParams);
                }
            } else {
                if (activeServiceParams.getUnlockingTime() > new Date().getTime()) {
                    ResponseDTO result = getResponse(activeServiceParams);
                    PessimisticLockingThread.unschedule(activeServiceParams.getId());
                    return result;
                } else {
                    return TransmittedException.create("НЕВОЗМОЖНО ИЗМЕНИТЬ ДАННЫЕ! ИСТЕКЛО ВРЕМЯ ОЖИДАНИЯ ЗАПРОСА!").withExceptionType("exception");
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
        return TransmittedException.create("НЕВОЗМОЖНО ИЗМЕНИТЬ ДАННЫЕ!").withExceptionType("exception");
    }

}
