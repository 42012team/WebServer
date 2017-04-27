package classes.model.behavior.managers;

import classes.MessageSender;
import classes.transport.TransportServiceMessage;


import javax.jms.JMSException;
import java.math.BigInteger;
import java.util.Date;

public class JmsManager {
    private static String DEF_QUEUE = "test.in";


    public boolean isAvailable(int id, int serviceId, String processor, String message, Date date) {


        try {
            MessageSender messageSender = new MessageSender();
            messageSender.send(id, serviceId, processor, message, date);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        //отправка Оксане,возврат ответа .
        return true;
    }
}
