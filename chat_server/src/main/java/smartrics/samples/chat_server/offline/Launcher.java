package smartrics.samples.chat_server.offline;

import smartrics.samples.chat_server.Chat;
import smartrics.samples.chat_server.Protocol;
import smartrics.samples.chat_server.ProtocolParser;
import smartrics.samples.chat_server.User;


public class Launcher {
    public static void main(String[] args) {
        Chat chat = new Chat();
        ProtocolParser parser = new ProtocolParser();
        OfflineServer server = new OfflineServer(chat, parser);
        OfflineClient client1 = new OfflineClient("User1", server);
        OfflineClient client2 = new OfflineClient("User2", server);

        User user1 = new User(client1);
        user1.execute(new Protocol.Join("foo"));

        User user2 = new User(client2);
        user2.execute(new Protocol.Join("foo"));

        user2.execute(new Protocol.Send("foo", "user2 sends this"));
        user1.execute(new Protocol.Send("foo", "user1 sends that"));
        user1.execute(new Protocol.Send("foo", "user1 sends also that too"));

    }
}
