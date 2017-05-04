package smartrics.samples.chat_server.server;


public interface Client {

    String getId();

    void dispatch(String line);

    void receive(String room, String message);

    void addReceiver(Receiver receiver);
}
