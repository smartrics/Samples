package smartrics.samples.chat_server.server.offline;

import smartrics.samples.chat_server.protocol.Command;
import smartrics.samples.chat_server.protocol.ProtocolParser;
import smartrics.samples.chat_server.server.Client;
import smartrics.samples.chat_server.server.Server;
import smartrics.samples.chat_server.service.Chat;


public class OfflineServer implements Server {

    private final ProtocolParser parser;
    private Chat chat;

    public OfflineServer(Chat chat, ProtocolParser parser) {
        this.chat = chat;
        this.parser = parser;
    }

    @Override
    public void receive(Client client, String line) {
        Command command = parser.parse(line);
        chat.execute(client, command);
    }
}
