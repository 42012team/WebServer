package classes;

import javax.ejb.MessageDriven;

@MessageDriven(mappedName = "topicDestination")
public class TopicMessageConsumer extends AbstractMessageConsumer {
}
