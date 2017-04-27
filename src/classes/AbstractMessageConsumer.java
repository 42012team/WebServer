package classes;


import classes.transport.TransportServiceMessage;
import classes.util.InMemoryStorage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public abstract class AbstractMessageConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TransportServiceMessage textMessage = null;
        try {
            textMessage = (TransportServiceMessage) ((ObjectMessage) message).getObject();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        putToStorage("gfgfgf " + textMessage.getMessageForConsumer() + " gfgfgf");
    }

    protected void putToStorage(String message) {
        InMemoryStorage.add(getClass().getName() + " - " + message);
    }
}