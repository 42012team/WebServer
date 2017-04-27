package classes;

import classes.transport.TransportServiceMessage;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

@WebServlet("/send")
public class MessageSender extends HttpServlet {

    @Resource(name = "connectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "queueDestination")
    private Destination queue;

    @Resource(name = "topicDestination")
    private Destination topic;

    public void send(int id, int serviceId, String processor, String message, Date date) throws JMSException {
        TransportServiceMessage transportMessage = new TransportServiceMessage();
        transportMessage.setActiveServiceId(new BigInteger(String.valueOf(id)));
        transportMessage.setServiceId(new BigInteger(String.valueOf(serviceId)));
        transportMessage.setProcessor(processor);
        transportMessage.setMessageForConsumer(message);
        transportMessage.setDate(date);
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createObjectMessage(transportMessage));
        connection.close();

    }
}
