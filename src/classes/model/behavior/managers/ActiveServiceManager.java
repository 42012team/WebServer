package classes.model.behavior.managers;

import classes.activator.ActivatorInterface;
import classes.exceptions.TransmittedException;
import classes.idgenerator.IdGenerator;
import classes.model.ActiveService;
import classes.model.ActiveServiceParams;
import classes.model.ActiveServiceState;
import classes.model.ActiveServiceStatus;
import classes.model.behavior.storages.ActiveServiceStorage;

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


    public void setNextActiveService(int currentId, int newId) {
        activeServiceStorage.setNextId(currentId, newId);
    }

    public ActiveService createActiveService(ActiveServiceParams activeServiceParams) {
        if (!isExisting(activeServiceParams)) {
            ActiveService activeService = new ActiveService();
            int newId = idGenerator.generateId();
            activeService.setId(newId);
            activeService.setUserId(activeServiceParams.getUserId());
            activeService.setServiceId(activeServiceParams.getServiceId());
            activeService.setDate(activeServiceParams.getDate());
            activeService.setFirstStatus(activeServiceParams.getFirstStatus());
            activeService.setSecondStatus(activeServiceParams.getSecondStatus());
            activeService.setVersion(0);
            activeService.setNextActiveServiceId(0);
            activeService.setState(activeServiceParams.getState());
            try {
                storeActiveServices(Collections.singletonList(activeService));
            } catch (Exception ex) {
                return null;
            }
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
        activeService.setFirstStatus(activeServiceParams.getFirstStatus());
        activeService.setSecondStatus(activeServiceParams.getSecondStatus());
        activeService.setState(activeServiceParams.getState());
        activeService.setVersion(0);
        try {
            storeActiveServices(Collections.singletonList(activeService));
        } catch (Exception ex) {
            return null;
        }

        setNextActiveService(activeServiceParams.getOldActiveServiceId(), newId);
        activator.schedule(activeService);
        return activeService;
    }


    public void changeActiveServiceStatus(ActiveService activeService, ActiveServiceStatus currentStatus,
                                          ActiveServiceStatus newStatus) {
        activeService.setFirstStatus(currentStatus);
        activeService.setSecondStatus(newStatus);
    }

    public void changeState(ActiveService activeService) {
        activeService.setState(ActiveServiceState.READY);
    }

    public void changeActiveServiceDate(ActiveService activeService, Date date) {
        activeService.setDate(date);
    }

    public void changeActiveService(ActiveService activeService, ActiveServiceParams activeServiceParams) throws Exception {
        if (activeServiceParams.getState().equals(ActiveServiceState.READY)) {
            int newId = idGenerator.generateId();
            ActiveService newActiveService = new ActiveService();
            newActiveService.setId(newId);
            newActiveService.setUserId(activeServiceParams.getUserId());
            newActiveService.setServiceId(activeServiceParams.getServiceId());
            newActiveService.setDate(activeServiceParams.getDate());
            newActiveService.setFirstStatus(activeServiceParams.getFirstStatus());
            newActiveService.setSecondStatus(activeServiceParams.getSecondStatus());
            newActiveService.setVersion(0);
            newActiveService.setState(ActiveServiceState.NOT_READY);
            storeActiveServices(Collections.singletonList(newActiveService));
            activator.schedule(newActiveService);
            if ((activeService.getFirstStatus().equals(ActiveServiceStatus.DISCONNECTED))) {
                ActiveService oldActiveService = getActiveServiceById(activeService.getId());
                setNextActiveService(newId, oldActiveService.getNextActiveServiceId());
            }
            setNextActiveService(activeService.getId(), newId);
            if (activeService.getNextActiveServiceId() != 0) {
                setNextActiveService(newId, activeService.getNextActiveServiceId());
            }
        } else {
                changeActiveServiceDate(activeService, activeServiceParams.getDate());
                changeActiveServiceStatus(activeService, activeServiceParams.getFirstStatus(),
                        activeServiceParams.getSecondStatus());
                activeService.setVersion(activeServiceParams.getVersion() + 1);
                activeService.setState(ActiveServiceState.NOT_READY);
                String message = "Изменение статуса услуги с " + activeServiceParams.getFirstStatus() + " на " + activeServiceParams.getSecondStatus() +
                        " ";
                storeActiveServices(Collections.singletonList(activeService));
            activator.reschedule(activeService);
                }


    }


    public void storeActiveServices(List<ActiveService> activeServicesList) throws Exception {
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
        try {
            if (activeService.getState().equals(ActiveServiceState.READY)) {
                int newId = idGenerator.generateId();
                ActiveService newActiveService = new ActiveService();
                newActiveService.setId(newId);
                newActiveService.setUserId(activeService.getUserId());
                newActiveService.setServiceId(activeService.getServiceId());
                newActiveService.setDate(new Date());
                newActiveService.setFirstStatus(activeService.getSecondStatus());
                newActiveService.setSecondStatus(ActiveServiceStatus.DISCONNECTED);
                newActiveService.setVersion(0);
                newActiveService.setState(ActiveServiceState.CANCELLED);
                storeActiveServices(Collections.singletonList(newActiveService));
                setNextActiveService(activeService.getId(), newId);
            } else {
                ActiveService previousActiveService = activeServiceStorage.getPreviousActiveService(activeServiceId);
                if (previousActiveService != null) {
                    if (previousActiveService.getState().equals(ActiveServiceState.READY)) {
                        int newId = idGenerator.generateId();
                        ActiveService newActiveService = new ActiveService();
                        newActiveService.setId(newId);
                        newActiveService.setUserId(previousActiveService.getUserId());
                        newActiveService.setServiceId(previousActiveService.getServiceId());
                        newActiveService.setDate(new Date());
                        newActiveService.setFirstStatus(previousActiveService.getSecondStatus());
                        newActiveService.setSecondStatus(ActiveServiceStatus.DISCONNECTED);
                        newActiveService.setVersion(0);
                        newActiveService.setState(ActiveServiceState.CANCELLED);
                        storeActiveServices(Collections.singletonList(newActiveService));
                        setNextActiveService(activeService.getId(), newId);
                    } else {
                        ActiveService previousPreviousActiveService = activeServiceStorage.getPreviousActiveService(previousActiveService.getId());
                        if (previousPreviousActiveService != null) {
                            setNextActiveService(previousPreviousActiveService.getId(), 0);
                        }
                        activator.unschedule(previousActiveService);
                        activeServiceStorage.deleteActiveService(previousActiveService.getId());
                    }
                }
                activator.unschedule(activeService);
                activeServiceStorage.deleteActiveService(activeService.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ActiveService> getAllActiveServices() {
        return activeServiceStorage.getAllActiveServices();
    }

    public void deleteActiveServicesByUserId(int userId) {
        List<ActiveService> activeServices = activeServiceStorage.getActiveServicesByUserId(userId);
        activeServiceStorage.deleteActiveServicesByUserId(userId);
        for (ActiveService activeService : activeServices) {
            if (activeService.getSecondStatus() != null) {
                activator.unschedule(activeService);
                break;
            }
        }
    }

    public List<ActiveService> getActiveServicesHistoryByUserId(int userId, int serviceId) {
        return activeServiceStorage.getActiveServicesHistoryByUserId(userId, serviceId);
    }
    public void cancelLock(int activeServiceId){
        activator.unschedule(activeServiceStorage.getActiveServiceById(activeServiceId));
        activeServiceStorage.deleteActiveService(activeServiceId);
        activeServiceStorage.deleteNextActiveServiceId(activeServiceId);
    }

    public void changeNewTariffDate(int activeServiceId, Date date) throws TransmittedException {
        ActiveService previousPreviousActiveService =
                activeServiceStorage
                        .getPreviousActiveService(activeServiceStorage.getPreviousActiveService(activeServiceId).getId());
        if (previousPreviousActiveService.getState().equals(ActiveServiceState.NOT_READY)) {
            if (previousPreviousActiveService.getDate().compareTo(date) >= 0)
                throw TransmittedException.create("Невозможно изменить данные");

        }
        activeServiceStorage.changeNewTariffDate(activeServiceId,date);
        ActiveService activeService=activeServiceStorage.getActiveServiceById(activeServiceId);
        activator.reschedule(activeService);
        activator.reschedule(activeServiceStorage.getPreviousActiveService(activeServiceId));
    }

}
