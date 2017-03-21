package classes.configuration;

import classes.activator.Activator;
import classes.controllers.WebController;
import classes.idgenerator.IdGenerator;
import classes.idgenerator.IdGeneratorSingletonDB;
import classes.model.behavior.managers.ActiveServiceManager;
import classes.model.behavior.managers.ServiceManager;
import classes.model.behavior.managers.UserManager;
import classes.model.behavior.storages.impl.DBActiveServiceStorage;
import classes.model.behavior.storages.impl.DBServiceStorage;
import classes.model.behavior.storages.impl.DBUserStorage;
import classes.processors.Configuration;
import classes.processors.ConfigurationXML;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;

import java.util.Map;

public class Initialization {

    private static volatile Initialization instance = null;
    WebController controller = null;
  ///  private static Initialization instance = new Initialization();
    private Initialization() {

        IdGenerator idGenerator = IdGeneratorSingletonDB.getInstance();
        UserManager userManager = new UserManager(new DBUserStorage(), idGenerator);
        ServiceManager serviceManager = new ServiceManager(new DBServiceStorage(), idGenerator);
        ActiveServiceManager activeServiceManager = new ActiveServiceManager(new DBActiveServiceStorage(),
                idGenerator, serviceManager);
        serviceManager.setActiveServiceManager(activeServiceManager);
      //  Initializer initializer = new Initializer(userManager, activeServiceManager, serviceManager, "optimistic");
     //   Configuration c = new ConfigurationXML();
       // Map<String, RequestProcessor> map = c.getMap(initializer);
      //  controller = new WebController(map);
        controller=new WebController();
        Activator activator = new Activator();
        activeServiceManager.setActivator(activator);
        activator.setActiveServiceManager(activeServiceManager);
        activator.start();

    }

    public static Initialization getInstance() {
        if (instance == null) {
          synchronized (Initialization.class) {
                if (instance == null) {
                    instance = new Initialization();
               }
            }
        }
        return instance;
    }
    public void setController(WebController controller){
       this.controller=controller;
    }
    public WebController initialization() {
        synchronized (instance) {
            return controller;
        }
    }

}
