package classes.model.behavior.managers;

import classes.AbstractMessageConsumer;
import classes.MessageSenderBean;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Date;

public class JmsManager {

    public String isAvailable(int id, int serviceId, String message, Date date) {
        Object obj = new Object();
        StringBuffer stringBuffer = new StringBuffer();
        try {
            InitialContext initialContext = new InitialContext();
            MessageSenderBean messageSender = (MessageSenderBean) initialContext.lookup("java:global/WebServer_war_exploded/MessageSenderBean");
            AbstractMessageConsumer.putInPool(id, obj, stringBuffer);
            System.out.println(obj.hashCode() + "hash 1");
            messageSender.send(id, serviceId, message, date);
            synchronized (obj) {
                obj.wait();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return stringBuffer.toString();
    }
}
