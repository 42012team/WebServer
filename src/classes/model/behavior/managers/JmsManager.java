package classes.model.behavior.managers;

import classes.AbstractMessageConsumer;
import classes.MessageSender;

import javax.jms.JMSException;
import java.util.Date;

public class JmsManager {

    public String isAvailable(int id, int serviceId, String processor, String message, Date date) {
        try {
            MessageSender messageSender = new MessageSender();
            messageSender.send(id, serviceId, processor, message, date);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        Object obj = new Object();
        StringBuffer stringBuffer = new StringBuffer();
        AbstractMessageConsumer.putInPool(id, obj, stringBuffer);
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }
}
