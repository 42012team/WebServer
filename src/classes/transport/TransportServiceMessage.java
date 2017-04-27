package classes.transport;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Оксана on 20.04.2017.
 */
public class TransportServiceMessage implements ITransportMessage, Serializable {
    private String messageForConsumer;
    private String processor;
    private BigInteger serviceId;
    private BigInteger activeServiceId;
    private Date date;

    public String getMessageForConsumer() {
        return messageForConsumer;
    }

    public void setMessageForConsumer(String messageForConsumer) {
        this.messageForConsumer = messageForConsumer;
    }

    /* -------------------------------------------------------------------- */

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    /* -------------------------------------------------------------------- */

    public BigInteger getServiceId() {
        return serviceId;
    }

    public void setServiceId(BigInteger serviceId) {
        this.serviceId = serviceId;
    }

    /* -------------------------------------------------------------------- */

    public BigInteger getActiveServiceId() {
        return activeServiceId;
    }

    public void setActiveServiceId(BigInteger activeServiceId) {
        this.activeServiceId = activeServiceId;
    }

    /* -------------------------------------------------------------------- */

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
