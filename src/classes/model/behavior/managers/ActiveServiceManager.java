package classes.model.behavior.managers;

import classes.activator.ActivatorInterface;
import classes.idgenerator.IdGenerator;
import classes.model.ActiveService;
import classes.model.ActiveServiceParams;
import classes.model.ActiveServiceStatus;
import classes.model.behavior.storages.ActiveServiceStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ActiveServiceManager {

    ActiveServiceStorage activeServiceStorage;
    ServiceManager serviceManager;
    IdGenerator idGenerator;
    ActivatorInterface activator;

    public ActiveServiceManager(ActiveServiceStorage activeServiceStorage, IdGenerator idGenerator,
                                ServiceManager serviceManager) {
        this.activeServiceStorage = activeServiceStorage;
        this.idGenerator = idGenerator;
        this.serviceManager = serviceManager;
    }

    public void setActivator(ActivatorInterface activator) {
        this.activator = activator;
    }

    public List<ActiveService> getActiveServicesByUserId(int userId) {
        return activeServiceStorage.getActiveServicesByUserId(userId);
    }

    public ActiveService getActiveServiceById(int activeServiceId) {
        return activeServiceStorage.getActiveServiceById(activeServiceId);
    }

    public void createActiveServiceWithNewStatus(ActiveService activeService) {
        int currentId = activeService.getId();
        int newId = idGenerator.generateId();
        activeService.setId(newId);
        activeService.setVersion(0);
        storeActiveServices(Collections.singletonList(activeService));
        if ((activeService.getCurrentStatus().equals(ActiveServiceStatus.DISCONNECTED))) {
            ActiveService activeService1 = getActiveServiceById(currentId);
            setNextActiveService(newId, activeService1.getNextActiveServiceId());
        }
        setNextActiveService(currentId, newId);

    }

    public void setNextActiveService(int currentId, int newId) {
        activeServiceStorage.setNextId(currentId, newId);
    }

    public ActiveService createActiveService(ActiveServiceParams activeServiceParams) {
        if (!isExisting(activeServiceParams)) {
            ActiveService activeService = new ActiveService();
            activeService.setId(idGenerator.generateId());
            activeService.setUserId(activeServiceParams.getUserId());
            activeService.setServiceId(activeServiceParams.getServiceId());
            activeService.setDate(activeServiceParams.getDate());
            activeService.setCurrentStatus(activeServiceParams.getCurrentStatus());
            activeService.setNewStatus(activeServiceParams.getNewStatus());
            activeService.setVersion(0);
            storeActiveServices(Collections.singletonList(activeService));
            activator.schedule(activeService);
            return activeService;
        }
        return null;
    }

    public ActiveService changeTariff(ActiveServiceParams activeServiceParams) {
        int newId = idGenerator.generateId();
        ActiveService activeService = new ActiveService();
        activeService.setId(newId);
        activeService.setUserId(activeServiceParams.getUserId());
        activeService.setServiceId(activeServiceParams.getServiceId());
        activeService.setDate(activeServiceParams.getDate());
        activeService.setCurrentStatus(ActiveServiceStatus.PLANNED);
        activeService.setNewStatus(ActiveServiceStatus.ACTIVE);
        activeService.setVersion(0);
        storeActiveServices(Collections.singletonList(activeService));
        setNextActiveService(activeServiceParams.getOldActiveServiceId(), newId);
        activator.schedule(activeService);
        return activeService;

    }


    public void changeActiveServiceStatus(ActiveService activeService, ActiveServiceStatus currentStatus,
                                          ActiveServiceStatus newStatus) {
        activeService.setCurrentStatus(currentStatus);
        activeService.setNewStatus(newStatus);

    }

    public void changeActiveServiceDate(ActiveService activeService, Date date) {
        activeService.setDate(date);
    }

    public void changeActiveService(ActiveService activeService, ActiveServiceParams activeServiceParams) {
        changeActiveServiceDate(activeService, activeServiceParams.getDate());
        changeActiveServiceStatus(activeService, activeServiceParams.getCurrentStatus(),
                activeServiceParams.getNewStatus());
        activeService.setVersion(activeServiceParams.getVersion() + 1);
        String message = "Изменение статуса услуги с " + activeServiceParams.getCurrentStatus() + " на " + activeServiceParams.getNewStatus() +
                " ";
        storeActiveServices(Collections.singletonList(activeService));
        activator.reschedule(activeService);
    }

    public List<String> getHistory(int activeServiceId) {
        return activeServiceStorage.getHistoryById(activeServiceId);
    }

    public void storeActiveServices(List<ActiveService> activeServicesList) {
        activeServiceStorage.storeActiveServices(activeServicesList);
    }

    public boolean isExisting(ActiveServiceParams activeServiceParams) {
        boolean hasThisType = false;
        String type = serviceManager.getServiceById(activeServiceParams.getServiceId()).getType();
        List<ActiveService> currentActiveServices = getActiveServicesByUserId(activeServiceParams.getUserId());
        for (ActiveService activeService : currentActiveServices) {
            if (type.equals(serviceManager.getServiceById(activeService.getServiceId()).getType())) {
                hasThisType = true;
                break;
            }
        }
        return hasThisType;
    }

    public void deleteActiveService(int activeServiceId) {
        ActiveService activeService = activeServiceStorage.getActiveServiceById(activeServiceId);
        if (activeService.getNewStatus() != null) {
            activeServiceStorage.deleteActiveService(activeServiceId);
            activator.unschedule(activeService);
        } else {
            activeServiceStorage.deleteActiveService(activeServiceId);
        }
    }

    public void deleteActiveServicesWithTheSameType(int activeServiceId) {
        ActiveService activeService = getActiveServiceById(activeServiceId);
        changeActiveServiceStatus(activeService, ActiveServiceStatus.DISCONNECTED, null);
        changeActiveServiceDate(activeService, new Date());
        createActiveServiceWithNewStatus(activeService);
        activeServiceStorage.deleteActiveServicesWithTheSameType(activeServiceId);

    }

    public void cancelChangingTariff(int activeServiceId) {
        activeServiceStorage.cancelChangingTariff(activeServiceId);
    }

    public List<ActiveService> getAllActiveServices() {
        return activeServiceStorage.getAllActiveServices();
    }

    public void deleteActiveServicesByUserId(int userId) {
        List<ActiveService> activeServices = activeServiceStorage.getActiveServicesByUserId(userId);
        activeServiceStorage.deleteActiveServicesByUserId(userId);
        for (ActiveService activeService : activeServices) {
            if (activeService.getNewStatus() != null) {
                activator.unschedule(activeService);
                break;
            }
        }
    }

}
