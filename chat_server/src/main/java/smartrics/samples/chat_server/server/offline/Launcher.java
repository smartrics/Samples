package smartrics.samples.chat_server.server.offline;

import smartrics.samples.chat_server.service.Chat;
import smartrics.samples.chat_server.protocol.Join;
import smartrics.samples.chat_server.protocol.ProtocolParser;
import smartrics.samples.chat_server.ui.User;
import smartrics.samples.chat_server.protocol.Send;


public class Launcher {
    public static void main(String[] args) {
        Chat chat = new Chat();
        ProtocolParser parser = new ProtocolParser();
        OfflineServer server = new OfflineServer(chat, parser);
        OfflineClient client1 = new OfflineClient("User1", server);
        OfflineClient client2 = new OfflineClient("User2", server);
        OfflineClient client3 = new OfflineClient("User3", server);

        User user1 = new User(client1);
        user1.doCommand(new Join("foo"));

        User user2 = new User(client2);
        user2.doCommand(new Join("foo"));

        User user3 = new User(client3);
        user3.doCommand(new Join("foo"));

        user2.doCommand(new Send("foo", "user2 sends this"));
        user1.doCommand(new Send("foo", "user1 sends that"));
        user1.doCommand(new Send("foo", "user1 sends also that too"));

    }
}
