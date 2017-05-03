package smartrics.samples.chat_server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Room {
    private String name;
    private ConcurrentHashMap<String, Client> clients;


    public Room(String name) {
        this.name = name;
        this.clients = new ConcurrentHashMap<>();
    }

    public boolean hasClient(String id) {
        return clients.containsKey(id);
    }

    public void join(Client client) {
        clients.put(client.getId(), client);
    }

    public void dispatchToClients(String senderId, String message) {
        clients.values().forEach(client -> {
            if(!senderId.equals(client.getId())) {
                client.receive(name, message);
            }
        });
    }
}
