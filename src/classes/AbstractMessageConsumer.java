package classes;


import classes.model.ActiveService;
import classes.model.behavior.managers.ActiveServiceManager;
import classes.processors.Initializer;
import classes.transport.TransportActivator;
import classes.transport.TransportServiceMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.util.Collections;
import java.util.HashMap;
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
        try {
            if (((ObjectMessage) message).getObject() instanceof TransportServiceMessage) {
                TransportServiceMessage transportServiceMessage = (TransportServiceMessage) ((ObjectMessage) message)
                        .getObject();
                System.out.println("ВЫвод " + transportServiceMessage.getActiveServiceId().intValue() + " pool size=" +
                        pool.size() + ".");
                MapValue mapValue = pool.get(transportServiceMessage.getActiveServiceId().intValue());
                String s = transportServiceMessage.getMessageForConsumer();
                System.out.println(mapValue + s);
                mapValue.getS().append(s);
                System.out.println(mapValue.getObj().hashCode() + "hash 2");
                synchronized (mapValue.getObj()) {
                    mapValue.getObj().notify();
                }
                synchronized (pool) {
                    pool.remove(transportServiceMessage.getActiveServiceId().intValue());
                }
            } else {
                if (((ObjectMessage) message).getObject() instanceof TransportActivator) {
                    TransportActivator transportServiceMessage = (TransportActivator) ((ObjectMessage) message)
                            .getObject();
                    ActiveServiceManager activeServiceManager = Initializer.getActiveServiceManager();
                    ActiveService activatedActiveService = activeServiceManager.getActiveServiceById(transportServiceMessage.getActiveServiceId().intValue());
                    activeServiceManager.changeState(activatedActiveService);
                    activeServiceManager.storeActiveServices(Collections.singletonList(activatedActiveService));
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void putInPool(int id, Object obj, StringBuffer s) {
        synchronized (pool) {
            System.out.println("lalala");
            pool.put(id, new MapValue(obj, s));
        }
        System.out.println(pool.size());
    }
}