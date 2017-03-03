package classes.export_import;

import classes.model.Account;
import classes.model.ActiveService;
import classes.model.ActiveServiceStatus;
import classes.model.User;
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
 * Created by User on 28.02.2017.
 */
public class Import {
    //private final String PATH = "D:\\nc\\1.xml";
    Document doc;

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
                    Date date = null;
                    try {
                        if (!activeServiceElement.getAttribute("date").equals("")) {
                            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                            date = format.parse(activeServiceElement.getAttribute("date"));
                        }
                        ActiveService activeService = null;
                        ActiveServiceStatus newStatus = null;
                        ActiveServiceStatus currentStatus = null;
                        switch (activeServiceElement.getAttribute("currentStatus")) {
                            case "ACTIVE":
                                currentStatus = ActiveServiceStatus.ACTIVE;
                                break;
                            case "PLANNED":
                                currentStatus = ActiveServiceStatus.PLANNED;
                                break;
                            case "SUSPENDED":
                                currentStatus = ActiveServiceStatus.SUSPENDED;
                                break;
                            case "DISCONNECTED":
                                currentStatus = ActiveServiceStatus.DISCONNECTED;
                                break;
                        }
                        switch (activeServiceElement.getAttribute("newStatus")) {
                            case "ACTIVE":
                                newStatus = ActiveServiceStatus.ACTIVE;
                                break;
                            case "PLANNED":
                                newStatus = ActiveServiceStatus.PLANNED;
                                break;
                            case "SUSPENDED":
                                newStatus = ActiveServiceStatus.SUSPENDED;
                                break;
                            case "DISCONNECTED":
                                newStatus = ActiveServiceStatus.DISCONNECTED;
                                break;
                        }
                        activeService = new ActiveService(activeServiceId, serviceId, userId, currentStatus, newStatus, date);
                        activeService.setVersion(version);
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
