package classes.export_import;

import classes.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class responsible for ....
 *
 */
public class Import {
    //private final String PATH = "D:\\nc\\1.xml";
    Document doc;

    /**
     * imports ...
     * @param path path which should be ...
     */
    public Import(String path) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new File(path));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("Exception occured!");
            System.out.println("Ошибка при создании документа для записи!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        }
        doc.getDocumentElement().normalize();
    }

    public List<Account> getAccountList() {
        List<Account> accountList = new ArrayList<Account>();

        NodeList userNodes = doc.getElementsByTagName("user");

        for (int i = 0; i < userNodes.getLength(); i++) {

            Element userElement = (Element) userNodes.item(i);
            User user = new User(Integer.parseInt(userElement.getAttribute("id")),
                    userElement.getAttribute("name"), userElement.getAttribute("surname"),
                    userElement.getAttribute("email"), userElement.getAttribute("phone"),
                    userElement.getAttribute("address"), userElement.getAttribute("login"),
                    userElement.getAttribute("password"), Integer.parseInt(userElement.getAttribute("version")),
                    userElement.getAttribute("privilege"));

            Account account = new Account(user);
            List<ActiveService> activeServiceList = null;
            NodeList activeServiceNodes = userElement.getElementsByTagName("activeService");
            if (activeServiceNodes != null) {
                activeServiceList = new ArrayList<ActiveService>();
                for (int temp = 0; temp < activeServiceNodes.getLength(); temp++) {
                    Element activeServiceElement = (Element) activeServiceNodes.item(temp);
                    int activeServiceId = Integer.parseInt(activeServiceElement.getAttribute("id"));
                    int serviceId = Integer.parseInt(activeServiceElement.getAttribute("serviceId"));
                    int version = Integer.parseInt(activeServiceElement.getAttribute("version"));
                    int userId = Integer.parseInt(activeServiceElement.getAttribute("userId"));
                    int nextActiveServiceId=Integer.parseInt(activeServiceElement.getAttribute("nextActiveServiceId"));
                    Date date = null;
                    try {
                        if (!activeServiceElement.getAttribute("date").equals("")) {
                            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                            date = format.parse(activeServiceElement.getAttribute("date"));
                        }
                        ActiveService activeService = null;
                        ActiveServiceStatus secondStatus = null;
                        ActiveServiceStatus firstStatus = null;
                        ActiveServiceState state=null;
                        switch (activeServiceElement.getAttribute("firstStatus")) {
                            case "ACTIVE":
                                firstStatus = ActiveServiceStatus.ACTIVE;
                                break;
                            case "PLANNED":
                                firstStatus = ActiveServiceStatus.PLANNED;
                                break;
                            case "SUSPENDED":
                                firstStatus = ActiveServiceStatus.SUSPENDED;
                                break;
                            case "DISCONNECTED":
                                firstStatus = ActiveServiceStatus.DISCONNECTED;
                                break;
                        }
                        switch (activeServiceElement.getAttribute("secondStatus")) {
                            case "ACTIVE":
                                secondStatus = ActiveServiceStatus.ACTIVE;
                                break;
                            case "PLANNED":
                                secondStatus = ActiveServiceStatus.PLANNED;
                                break;
                            case "SUSPENDED":
                                secondStatus = ActiveServiceStatus.SUSPENDED;
                                break;
                            case "DISCONNECTED":
                                secondStatus = ActiveServiceStatus.DISCONNECTED;
                                break;
                        }
                        switch (activeServiceElement.getAttribute("state")) {
                            case "READY":
                                state= ActiveServiceState.READY;
                                break;
                            case "NOT_READY":
                                state= ActiveServiceState.NOT_READY;
                                break;
                            case "CANCELLED":
                                state= ActiveServiceState.CANCELLED;
                                break;
                        }
                        activeService = new ActiveService(activeServiceId, serviceId, userId, firstStatus, secondStatus, date,state);
                        activeService.setVersion(version);
                        activeService.setNextActiveServiceId(nextActiveServiceId);
                        activeServiceList.add(activeService);
                    } catch (ParseException ex) {
                        System.out.println("Exception occured!");
                        System.out.println("Ошибка в документе для записи!");
                        StackTraceElement[] stackTraceElements = ex.getStackTrace();
                        for (int l = stackTraceElements.length - 1; l >= 0; l--) {
                            System.out.println(stackTraceElements[l].toString());
                        }
                    }
                }
            }
            account.setActiveServices(activeServiceList);
            accountList.add(account);
        }

        return accountList;
    }
}
