package classes;
import classes.transport.TransportServiceMessage;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.*;
import java.math.BigInteger;
import java.util.Date;

@Startup
@Singleton(name = "MessageSenderBean")
public class MessageSenderBean {

    @Resource(name = "connectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "queueDestination")
    private Destination queue;

    public void send(int id, int serviceId, String message, Date date) throws JMSException {
        System.out.println("Посылаю " + id + ".");
        TransportServiceMessage transportMessage = new TransportServiceMessage();
        transportMessage.setActiveServiceId(new BigInteger(String.valueOf(id)));
        transportMessage.setServiceId(new BigInteger(String.valueOf(serviceId)));
        transportMessage.setMessageForConsumer(message);
        transportMessage.setDate(date);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createObjectMessage(transportMessage));
        connection.close();
    }
}
