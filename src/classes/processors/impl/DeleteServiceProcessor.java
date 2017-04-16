package classes.processors.impl;

import classes.exceptions.TransmittedException;
import classes.model.behavior.managers.ServiceManager;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.request.impl.TransmittedServiceParams;
import classes.response.ResponseDTO;
import classes.response.impl.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("deleteService")
public class DeleteServiceProcessor implements RequestProcessor, Serializable {

    private Initializer initializer;

    public DeleteServiceProcessor() {

    }

    @Autowired
    public void setInitializer(Initializer initializer) {
        this.initializer = initializer;
    }

    @Override
    public ResponseDTO process(RequestDTO request) {
        try {
            TransmittedServiceParams serviceParams = (TransmittedServiceParams) request;
            ServiceManager serviceManager = initializer.getServiceManager();
            System.out.println("Попытка удаления услуги с Id " + serviceParams.getServiceId());
            if (serviceManager.deleteService(serviceParams.getServiceId())) {
                System.out.println("Услуга успешно удалена!");
            } else {
                System.out.println("Статус услуги был изменен на DEPRECATED!");
            }
            return ServiceResponse.create().withResponseType("services");

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