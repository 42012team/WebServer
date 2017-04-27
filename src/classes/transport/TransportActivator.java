package classes.transport;

import java.math.BigInteger;

public class TransportActivator implements ITransportMessage {
    private BigInteger activeServiceId;
    private String processor;

    @Override
    public String getProcessor() {
        return processor;
    }

    @Override
    public void setProcessor(String processor) {
        this.processor = processor;
    }

    /* -------------------------------------------------------------------- */

    public BigInteger getActiveServiceId() {
        return activeServiceId;
    }

    public void setActiveServiceId(BigInteger activeServiceId) {
        this.activeServiceId = activeServiceId;
    }


}
