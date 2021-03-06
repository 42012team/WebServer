package classes.model.behavior.storages;

import classes.model.ActiveService;

import java.util.Date;
import java.util.List;

public interface ActiveServiceStorage {

    public List<ActiveService> getActiveServicesByUserId(int userId);

    public void deleteActiveService(int activeServiceId);

    public List<ActiveService> getAllActiveServices();

    public ActiveService getActiveServiceById(int activeServiceId);

    public void deleteNextActiveServiceId(int nextId);

    public void storeActiveServices(List<ActiveService> activeServicesList) throws Exception;

    public void deleteActiveServicesByUserId(int userId);

    public void setNextId(int currentId, int newId);

    public ActiveService getPreviousActiveService(int activeServiceId);

    public List<ActiveService> getActiveServicesHistoryByUserId(int userId, int serviceId);

    public void changeNewTariffDate(int activeServiceId,Date date);


}
