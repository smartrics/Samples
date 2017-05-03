package smartrics.samples.chat_server.offline;

import smartrics.samples.chat_server.*;


public class OfflineServer implements Server {

    private final ProtocolParser parser;
    private Chat chat;

    public OfflineServer(Chat chat, ProtocolParser parser) {
        this.chat = chat;
        this.parser = parser;
    }

    @Override
    public void receive(Client client, String line) {
        Protocol.Command command = parser.parse(line);
        chat.execute(client, command);
    }
}
