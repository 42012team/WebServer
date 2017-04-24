package classes;


import classes.util.InMemoryStorage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public abstract class AbstractMessageConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            putToStorage(textMessage.getText());
        } catch (JMSException ex) {
            putToStorage(ex.toString());
        }
    }

    protected void putToStorage(String message) {
        InMemoryStorage.add(getClass().getName() + " - " + message);
    }
}