package classes;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/send")
public class MessageSender extends HttpServlet {

    @Resource(name = "connectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "queueDestination")
    private Destination queue;

    @Resource(name = "topicDestination")
    private Destination topic;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("msg");
        String type = req.getParameter("type");
        try {
            send(type, message);
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/index1.jsp");
    }

    private void send(String type, String text) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer;
        switch (type) {
            case "Topic":
                producer = session.createProducer(topic);
                break;
            case "Queue":
                producer = session.createProducer(queue);
                break;
            default:
                throw new UnsupportedOperationException("Unknown destination " + type);
        }
        producer.send(session.createTextMessage(text));
        connection.close();

    }
}
