package smartrics.samples.chat_server;

/**
 * Created by 702161900 on 03/05/2017.
 */
public interface Receiver {

    void newMessage(String room, String message);
}
