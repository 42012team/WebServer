package classes.export_import;

import classes.model.Account;
import classes.model.ActiveService;
import classes.model.User;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by User on 28.02.2017.
 */
public class Export {
    private final String PATH = "D:\\nc\\1.xml";
    Document doc;

    public Export() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new File(PATH));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("Exception occured!");
            System.out.println("Ошибка при создании документа для записи!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        }
    }

    public void storeAccount(List<Account> accountList) {
        System.out.println("start store" + accountList.size());
        for (int i = 0; i < accountList.size(); i++) {
            User user = accountList.get(i).getUser();
            System.out.println(user.getId());
            Element rootElement = (Element) doc.getElementsByTagName("users").item(0);
            Element elementUser = doc.createElement("user");
            Attr attr = doc.createAttribute("name");
            attr.setValue(user.getName());
            elementUser.setAttributeNode(attr);
            attr = doc.createAttribute("id");
            attr.setValue(String.valueOf(user.getId()));
            elementUser.setAttributeNode(attr);
            attr = doc.createAttribute("surname");
            attr.setValue(user.getSurname());
            elementUser.setAttributeNode(attr);
            attr = doc.createAttribute("email");
            attr.setValue(user.getEmail());
            elementUser.setAttributeNode(attr);
            attr = doc.createAttribute("phone");
            attr.setValue(user.getPhone());
            elementUser.setAttributeNode(attr);
            attr = doc.createAttribute("address");
            attr.setValue(user.getAddress());
            elementUser.setAttributeNode(attr);
            attr = doc.createAttribute("login");
            attr.setValue(user.getLogin());
            elementUser.setAttributeNode(attr);
            attr = doc.createAttribute("password");
            attr.setValue(user.getPassword());
            elementUser.setAttributeNode(attr);
            attr = doc.createAttribute("version");
            attr.setValue(Integer.toString(user.getVersion()));
            elementUser.setAttributeNode(attr);
            attr = doc.createAttribute("privilege");
            attr.setValue(user.getPrivilege());
            elementUser.setAttributeNode(attr);
            rootElement.appendChild(elementUser);
            List<ActiveService> activeServicesList = accountList.get(i).getActiveServices();
            for (int j = 0; j < accountList.get(i).getActiveServices().size(); j++) {
                Element activeServiceElement = doc.createElement("activeService");
                attr = doc.createAttribute("id");
                attr.setValue(String.valueOf(activeServicesList.get(j).getId()));
                activeServiceElement.setAttributeNode(attr);
                attr = doc.createAttribute("serviceId");
                attr.setValue(String.valueOf(activeServicesList.get(j).getServiceId()));
                activeServiceElement.setAttributeNode(attr);
                attr = doc.createAttribute("userId");
                attr.setValue(String.valueOf(activeServicesList.get(j).getUserId()));
                activeServiceElement.setAttributeNode(attr);
                if (activeServicesList.get(j).getDate() != null) {
                    SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                    String strDate = sdfDate.format(activeServicesList.get(j).getDate());
                    attr = doc.createAttribute("date");
                    attr.setValue(strDate);
                    activeServiceElement.setAttributeNode(attr);
                } else {
                    attr = doc.createAttribute("date");
                    attr.setValue("");
                }
                attr = doc.createAttribute("currentStatus");
                attr.setValue(activeServicesList.get(j).getCurrentStatus().toString());
                activeServiceElement.setAttributeNode(attr);
                if (activeServicesList.get(j).getNewStatus() != null) {
                    attr = doc.createAttribute("newStatus");
                    attr.setValue(activeServicesList.get(j).getNewStatus().toString());
                    activeServiceElement.setAttributeNode(attr);
                } else {
                    attr = doc.createAttribute("newStatus");
                    attr.setValue("");
                }
                attr = doc.createAttribute("version");
                attr.setValue(Integer.toString(activeServicesList.get(j).getVersion()));
                activeServiceElement.setAttributeNode(attr);
                elementUser.appendChild(activeServiceElement);


            }
        }
        write(doc);
    }

    private void write(Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PATH));
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.out.println("Exception occured!");
            System.out.println("Ошибка записи в документ!");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            for (int i = stackTraceElements.length - 1; i >= 0; i--) {
                System.out.println(stackTraceElements[i].toString());
            }
        }
    }

}
