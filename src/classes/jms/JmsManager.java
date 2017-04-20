package classes.jms;

public class JmsManager {
    private static String DEF_QUEUE = "test.in";


    public boolean isAvailable(int id, int serviceId, String processor, String message) {
        ITransportMessage transportMessage = new TransportServiceMessage(id, serviceId, processor, message);
        //отправка Оксане,возврат ответа .
        return true;
    }
}
