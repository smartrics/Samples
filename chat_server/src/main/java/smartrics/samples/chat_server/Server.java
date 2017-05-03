package smartrics.samples.chat_server;


public interface Server {

    void receive(Client client, String line);
}
