package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.behavior.managers.ServiceManager;
import classes.pessimisticLock.PessimisticLockingThread;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ServiceResponse;

import java.io.Serializable;
import java.util.Date;

public class DeleteServiceProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public DeleteServiceProcessor() {

    }

    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedServiceParams serviceParams = (TransmittedServiceParams) request;
            if (initializer.getTypeOfLock().equals("optimistic")) {
                if (initializer.getServiceManager()
                        .getServiceById(serviceParams.getServiceId()) != null) {
                    ServiceManager serviceManager = initializer.getServiceManager();
                    System.out.println("Попытка удаления услуги с Id " + serviceParams.getServiceId());
                    if (serviceManager.deleteService(serviceParams.getServiceId())) {
                        System.out.println("Услуга успешно удалена!");
                    } else {
                        System.out.println("Статус услуги был изменен на DEPRECATED!");
                    }
                    return ServiceResponse.create().withResponseType("services").withServices(initializer.getServiceManager().getAllServices());
                }
            } else {
                if (serviceParams.getUnlockingTime() > new Date().getTime()) {
                    ServiceManager serviceManager = initializer.getServiceManager();
                    System.out.println("Попытка удаления услуги с Id " + serviceParams.getServiceId());
                    if (serviceManager.deleteService(serviceParams.getServiceId())) {
                        System.out.println("Услуга успешно удалена!");
                    } else {
                        System.out.println("Статус услуги был изменен на DEPRECATED!");
                    }
                    PessimisticLockingThread.unschedule(serviceParams.getServiceId());
                    return ServiceResponse.create().withResponseType("services").withServices(initializer.getServiceManager().getAllServices());
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