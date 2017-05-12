package classes;

import javax.ejb.MessageDriven;

@MessageDriven(mappedName = "queueDestination")
public class QueueMessageConsumer extends AbstractMessageConsumer {
}
