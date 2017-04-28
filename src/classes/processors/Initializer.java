package classes.processors;

import classes.model.behavior.managers.*;

public class Initializer {

    private UserManager userManager;
    private static ActiveServiceManager activeServiceManager;
    private ServiceManager serviceManager;
    private LockingManager lockingManager;
    private JmsManager jmsManager;

    public Initializer(UserManager userManager, ActiveServiceManager activeServiceManager, ServiceManager serviceManager,
                       LockingManager lockingManager, JmsManager jmsManager) {
        this.userManager = userManager;
        this.activeServiceManager = activeServiceManager;
        this.serviceManager = serviceManager;
        this.lockingManager = lockingManager;
        this.jmsManager = jmsManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public static ActiveServiceManager getActiveServiceManager() {
        return activeServiceManager;
    }

    public LockingManager getLockingManager() {
        return lockingManager;
    }

    public JmsManager getJmsManager() {
        return jmsManager;
    }
}
