package classes.transport;

import java.io.Serializable;

public interface ITransportMessage extends Serializable {
    String getProcessor();

    void setProcessor(String processor);
}
