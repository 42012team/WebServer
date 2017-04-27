package classes;


import classes.transport.TransportActivator;
import classes.transport.TransportServiceMessage;
import classes.util.InMemoryStorage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMessageConsumer implements MessageListener {
    Map<Integer, Object> pool = new HashMap<Integer, Object>();

    @Override
    public void onMessage(Message message) {
        TransportServiceMessage textMessage = null;
        try {
            if (((ObjectMessage) message).getObject() instanceof TransportServiceMessage) {
                TransportServiceMessage transportServiceMessage = (TransportServiceMessage) ((ObjectMessage) message).getObject();
                pool.get(transportServiceMessage.getActiveServiceId().intValue()).notifyAll();
                pool.remove(transportServiceMessage.getActiveServiceId().intValue());
                //    textMessage = (TransportServiceMessage) ((ObjectMessage) message).getObject();
            } else {


            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
        putToStorage("gfgfgf " + textMessage.getMessageForConsumer() + " gfgfgf");
    }

    protected void putToStorage(String message) {
        InMemoryStorage.add(getClass().getName() + " - " + message);
    }

    public void putInPool(int id, Object object) {
        synchronized (pool) {
            pool.put(id, object);
        }
    }
}