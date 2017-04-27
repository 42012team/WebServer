package classes.model.behavior.managers;

import classes.AbstractMessageConsumer;
import classes.MessageSender;
import classes.TopicMessageConsumer;
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
        if (stringBuffer.equals("success"))
            return true;
        else
            return false;
    }
}
