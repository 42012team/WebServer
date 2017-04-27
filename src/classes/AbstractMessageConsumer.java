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
    static class MapValue {
        Object obj;
        StringBuffer s;

        MapValue(Object obj, StringBuffer s) {
            this.obj = obj;
            this.s = s;
        }

        Object getObj() {
            return obj;
        }

        StringBuffer getS() {
            return s;
        }
    }

    static Map<Integer, MapValue> pool = new HashMap<Integer, MapValue>();

    @Override
    public void onMessage(Message message) {
        TransportServiceMessage textMessage = null;
        try {
            if (((ObjectMessage) message).getObject() instanceof TransportServiceMessage) {
                TransportServiceMessage transportServiceMessage = (TransportServiceMessage) ((ObjectMessage) message)
                        .getObject();
                MapValue mapValue = pool.get(transportServiceMessage.getActiveServiceId().intValue());
                String s = transportServiceMessage.getMessageForConsumer();
                mapValue.getS().append(s);
                mapValue.getObj().notifyAll();
                synchronized (pool) {
                    pool.remove(transportServiceMessage.getActiveServiceId().intValue());
                }
            } else {
//Здесь приходит айди подключенной услуги
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
        putToStorage("gfgfgf " + textMessage.getMessageForConsumer() + " gfgfgf");
    }

    protected void putToStorage(String message) {
        InMemoryStorage.add(getClass().getName() + " - " + message);
    }

    public static void putInPool(int id, Object obj, StringBuffer s) {
        synchronized (pool) {
            pool.put(id, new MapValue(obj, s));
        }
    }
}