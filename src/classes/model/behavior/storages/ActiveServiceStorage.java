package classes.model.behavior.storages;

import classes.model.ActiveService;

import java.util.List;

public interface ActiveServiceStorage {

    public List<ActiveService> getActiveServicesByUserId(int userId);

    public void deleteActiveService(int activeServiceId);

    public List<ActiveService> getAllActiveServices();

    public ActiveService getActiveServiceById(int activeServiceId);

    public void storeActiveServices(List<ActiveService> activeServicesList) throws Exception;

    public void deleteActiveServicesByUserId(int userId);

    public List<String> getHistoryById(int activeServiceId);

    public void deleteActiveServicesWithTheSameType(int activeServiceId);

    public void cancelChangingTariff(int activeServiceId);

    public void setNextId(int currentId, int newId);

    public List<ActiveService> getActiveServicesHistoryByUserId(int userId,int serviceId);
}
