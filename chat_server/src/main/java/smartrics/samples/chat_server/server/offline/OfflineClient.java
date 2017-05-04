package smartrics.samples.chat_server.server.offline;

import smartrics.samples.chat_server.server.Client;
import smartrics.samples.chat_server.server.Receiver;

import java.util.ArrayList;
import java.util.List;


public class OfflineClient implements Client {
    private final OfflineServer server;
    private final String id;
    private List<Receiver> receiverList;

    public OfflineClient(String id, OfflineServer server) {
        this.id = id;
        this.server = server;
        this.receiverList = new ArrayList<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void dispatch(String line) {
        server.receive(this, line);
    }

    @Override
    public void receive(String room, String message) {
        receiverList.forEach(receiver -> receiver.onNewMessage(room, message));
    }

    @Override
    public void addReceiver(Receiver receiver) {
        receiverList.add(receiver);
    }
}
