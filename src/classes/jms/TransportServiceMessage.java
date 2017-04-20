package classes.jms;

import java.io.Serializable;
import java.util.Date;


public class TransportServiceMessage implements ITransportMessage, Serializable {
    private String messageForConsumer;
    private String processor;
    private int serviceId;
    private int activeServiceId;
    private Date date;

    public TransportServiceMessage(int activeServiceId, int serviceId, String processor, String messageForConsumer) {
        this.activeServiceId = activeServiceId;
        this.serviceId = serviceId;
        this.processor = processor;
        this.messageForConsumer = messageForConsumer;

    }

    public String getMessageForConsumer() {
        return messageForConsumer;
    }

    public void setMessageForConsumer(String messageForConsumer) {
        this.messageForConsumer = messageForConsumer;
    }


    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }


    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }


    public int getActiveServiceId() {
        return activeServiceId;
    }

    public void setActiveServiceId(int activeServiceId) {
        this.activeServiceId = activeServiceId;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}