package classes.model.behavior.managers;

import classes.pessimisticLock.PessimisticLockingThread;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.request.impl.TransmittedServiceParams;
import classes.request.impl.TransmittedUserParams;

import java.util.Date;

public class LockingManager {

    private UserManager userManager;
    private ActiveServiceManager activeServiceManager;
    private ServiceManager serviceManager;
    private String typeOfLock;

    public LockingManager(UserManager userManager, ActiveServiceManager activeServiceManager,
                          ServiceManager serviceManager, String typeOfLock) {
        this.userManager = userManager;
        this.activeServiceManager = activeServiceManager;
        this.serviceManager = serviceManager;
        this.typeOfLock = typeOfLock;
    }

    public boolean isAvailableUser(TransmittedUserParams userParams) {
        if (typeOfLock.equals("optimistic")) {
            if ((userManager.getUserById(userParams.getUserId()) != null)
                    && (userManager.getUserById(userParams.getUserId())
                    .getVersion() == userParams.getVersion())) {
                return true;
            }
        } else {
            if (userParams.getUnlockingTime() > new Date().getTime()) {
                PessimisticLockingThread.unschedule(userParams.getUserId());
                return true;
            }
        }
        return false;
    }

    public boolean isAvailableService(TransmittedServiceParams serviceParams) {
        if (typeOfLock.equals("optimistic")) {
            if ((serviceManager.getServiceById(serviceParams.getServiceId()) != null)
                    && (serviceManager.getServiceById(serviceParams.getServiceId())
                    .getVersion() == serviceParams.getVersion())) {
                return true;
            }
        } else {
            if (serviceParams.getUnlockingTime() > new Date().getTime()) {
                PessimisticLockingThread.unschedule(serviceParams.getServiceId());
                return true;
            }
        }
        return false;
    }

    public boolean isAvailableActiveService(TransmittedActiveServiceParams activeServiceParams) {
        if (typeOfLock.equals("optimistic")) {
            if ((activeServiceManager.getActiveServiceById(activeServiceParams.getId()) != null)
                    && (activeServiceManager.getActiveServiceById(activeServiceParams.getId())
                    .getVersion() == activeServiceParams.getVersion())) {
                return true;
            }
        } else {
            if (activeServiceParams.getUnlockingTime() > new Date().getTime()) {
                PessimisticLockingThread.unschedule(activeServiceParams.getId());
                return true;
            }
        }
        return false;
    }
}
