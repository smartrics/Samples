package smartrics.samples.chat_server.server.online;

import smartrics.samples.chat_server.protocol.Join;
import smartrics.samples.chat_server.protocol.ProtocolParser;
import smartrics.samples.chat_server.protocol.Send;
import smartrics.samples.chat_server.ui.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientLauncher {

    public static void main(String[] args) throws Exception {
        ProtocolParser parser = new ProtocolParser();
        ExecutorService executorService = Executors.newCachedThreadPool();

        OnlineClient client1 = new OnlineClient(executorService, parser, "localhost", 8090);
        OnlineClient client2 = new OnlineClient(executorService, parser, "localhost", 8090);
        OnlineClient client3 = new OnlineClient(executorService, parser, "localhost", 8090);

        User user1 = new User(client1);
        user1.doCommand(new Join("foo"));

        User user2 = new User(client2);
        user2.doCommand(new Join("foo"));

        User user3 = new User(client3);
        user3.doCommand(new Join("foo"));

        Thread.sleep(1000);

        user1.doCommand(new Send("foo", "user1 sends that"));
        user2.doCommand(new Send("foo", "user2 sends this"));
        user1.doCommand(new Send("foo", "user1 sends also that too"));

        Thread.sleep(100000000);
    }
}
