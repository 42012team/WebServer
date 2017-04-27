package classes.model.behavior.managers;

import classes.jms.TransportServiceMessage;

public class JmsManager {
    private static String DEF_QUEUE = "test.in";


    public boolean isAvailable(int id, int serviceId, String processor, String message) {
        TransportServiceMessage transportMessage = new TransportServiceMessage();
        //отправка Оксане,возврат ответа .
        return true;
    }
}
