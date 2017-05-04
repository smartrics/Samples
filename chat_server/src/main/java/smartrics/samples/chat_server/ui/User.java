package smartrics.samples.chat_server.ui;

import smartrics.samples.chat_server.protocol.Command;
import smartrics.samples.chat_server.server.Client;
import smartrics.samples.chat_server.server.Receiver;

import java.util.concurrent.atomic.AtomicLong;

public class User implements Receiver {

    private final Client client;
    private AtomicLong count = new AtomicLong(0);

    public User(Client client) {
        this.client = client;
        this.client.addReceiver(this);
    }

    public void doCommand(Command command) {
        System.out.println("[User] " + client.getId() + " > '" + command.serialise().trim() + "'");
        client.dispatch(command.serialise());
    }


    @Override
    public void onNewMessage(String room, String message) {
        System.out.println("[User] " + client.getId() + "@" + room + " < " + message);
        count.incrementAndGet();
    }

    public long getCount() {
        return count.longValue();
    }
}
