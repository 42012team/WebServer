package classes.processors;

import classes.model.behavior.managers.ActiveServiceManager;
import classes.model.behavior.managers.LockingManager;
import classes.model.behavior.managers.ServiceManager;
import classes.model.behavior.managers.UserManager;

public class Initializer {

    private UserManager userManager;
    private ActiveServiceManager activeServiceManager;
    private ServiceManager serviceManager;
    private LockingManager lockingManager;

    public Initializer(UserManager userManager, ActiveServiceManager activeServiceManager, ServiceManager serviceManager,
                       LockingManager lockingManager) {
        this.userManager = userManager;
        this.activeServiceManager = activeServiceManager;
        this.serviceManager = serviceManager;
        this.lockingManager = lockingManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public ActiveServiceManager getActiveServiceManager() {
        return activeServiceManager;
    }

    public LockingManager getLockingManager() {
        return lockingManager;
    }

}
