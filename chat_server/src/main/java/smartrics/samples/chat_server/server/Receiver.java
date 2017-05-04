package smartrics.samples.chat_server.server;

public interface Receiver {

    void onNewMessage(String room, String message);
}
