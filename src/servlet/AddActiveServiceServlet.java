package servlet;

import classes.configuration.Initialization;
import classes.controllers.WebController;
import classes.exceptions.TransmittedException;
import classes.model.ActiveServiceState;
import classes.model.ActiveServiceStatus;
import classes.model.User;
import classes.request.impl.TransmittedActiveServiceParams;
import classes.response.ResponseDTO;
import classes.transport.TransportServiceMessage;

import javax.annotation.Resource;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActiveServiceServlet extends HttpServlet {
    WebController controller = null;
    @Resource(name = "connectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "queueDestination")
    private Destination queue;

    @Resource(name = "topicDestination")
    private Destination topic;

    @Override
    public void init() throws ServletException {
        controller = Initialization.getInstance().initialization();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = (User) request.getSession(true).getAttribute("user");
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String dateToString = request.getParameter("activationDate" + serviceId);
            dateToString = dateToString.replace('T', ' ');
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date newDate = null;
            newDate = format.parse(dateToString);
            if (newDate.before(new Date()) && (!((User) request.getSession(true).getAttribute("user")).getPrivilege()
                    .equals("admin"))) {
                throw new ServletException("НЕВЕРНЫЙ ВВОД ДАТЫ!");
            }
            try {
                send(123, serviceId, "do something", newDate);
            } catch (JMSException e) {
                e.printStackTrace();
            }
            TransmittedActiveServiceParams activeServiceParams = TransmittedActiveServiceParams.create()
                    .withServiceId(serviceId)
                    .withUserId(user.getId())
                    .withDate(newDate)
                    .withFirstStatus(ActiveServiceStatus.PLANNED)
                    .withSecondStatus(ActiveServiceStatus.ACTIVE)
                    .withState(ActiveServiceState.NOT_READY)
                    .withRequestType("createActiveService");
            ResponseDTO resp = controller.identifyObject(activeServiceParams);
            if (resp.getResponseType().equals("exception"))
                throw new ServletException(((TransmittedException) resp).getMessage());
            response.sendRedirect("/WebServer_war_exploded/ShowActiveServicesServlet");
        } catch (ParseException e) {
            throw new ServletException("НЕКОРРЕКТНАЯ ДАТА!");
        }
    }

    public void send(int id, int serviceId, String message, Date date) throws JMSException {
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
