package classes.main;

import classes.activator.Activator;
import classes.controllers.ProviderController;
import classes.idgenerator.IdGenerator;
import classes.idgenerator.IdGeneratorSingletonDB;
import classes.model.behavior.managers.ActiveServiceManager;
import classes.model.behavior.managers.ServiceManager;
import classes.model.behavior.managers.UserManager;
import classes.model.behavior.storages.impl.DBActiveServiceStorage;
import classes.model.behavior.storages.impl.DBServiceStorage;
import classes.model.behavior.storages.impl.DBUserStorage;
import classes.pessimisticLock.PessimisticLockingThread;
import classes.processors.Configuration;
import classes.processors.ConfigurationXML;
import classes.processors.Initializer;
import classes.processors.RequestProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {

    public static void main(String[] args) throws IOException {
        try {
            ////JDBC
            IdGenerator idGenerator = IdGeneratorSingletonDB.getInstance();
            UserManager userManager = new UserManager(new DBUserStorage(), idGenerator);
            ServiceManager serviceManager = new ServiceManager(new DBServiceStorage(), idGenerator);
            ActiveServiceManager activeServiceManager = new ActiveServiceManager(new DBActiveServiceStorage(),
                    idGenerator, serviceManager);


            ////XML
        /*   IdGenerator idGenerator = IdGeneratorSingleton.getInstance();
           UserManager userManager = new UserManager(new XMLUserStorage(), idGenerator);
           ServiceManager serviceManager = new ServiceManager(new XMLServiceStorage(), idGenerator);
            ActiveServiceManager activeServiceManager = new ActiveServiceManager(new XMLActiveServiceStorage(),
                   idGenerator, serviceManager);
        */
            ////HIBERNATE
          /*  IdGenerator idGenerator = IdGeneratorSingletonDB.getInstance();
            UserManager userManager = new UserManager(new UserStorageHibernate(), idGenerator);
            ServiceManager serviceManager = new ServiceManager(new ServiceStorageHibernate(), idGenerator);
            ActiveServiceManager activeServiceManager = new ActiveServiceManager(new ActiveServiceStorageHibernate(),
                    idGenerator, serviceManager);
*/
            serviceManager.setActiveServiceManager(activeServiceManager);
            Activator activator = new Activator();
            activeServiceManager.setActivator(activator);
            activator.setActiveServiceManager(activeServiceManager);
            activator.start();
            ServerSocket serverSocket = new ServerSocket(4141);
            String typeOfLock = "optimisti";
            Initializer initializer = new Initializer(userManager, activeServiceManager, serviceManager, typeOfLock);
            Configuration c = new ConfigurationXML();
            if (!typeOfLock.equals("optimistic")) {
                PessimisticLockingThread pessimisticLockingThread = new PessimisticLockingThread();
                pessimisticLockingThread.start();
            }
            Map<String, RequestProcessor> map = c.getMap(initializer);
            ProviderController cy = new ProviderController(map);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Runnable client = new ClientThread(clientSocket, cy);
                Thread clientThread = new Thread(client);
                clientThread.start();
            }

        } catch (Exception ex) {
            System.out.println("Exception occured!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        }
    }
}
