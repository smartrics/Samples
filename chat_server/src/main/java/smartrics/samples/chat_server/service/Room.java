package smartrics.samples.chat_server.service;

import smartrics.samples.chat_server.server.Client;

import java.util.concurrent.ConcurrentHashMap;

import static smartrics.samples.chat_server.util.Validation.checkNotNull;

/**
 * A chat room joined by many clients
 */
class Room {
    private String name;
    private ConcurrentHashMap<String, Client> clients;


    Room(String name) {
        this.name = name;
        this.clients = new ConcurrentHashMap<>();
    }

    boolean hasClient(String id) {
        return clients.containsKey(id);
    }

    void join(Client client) {
        final String id = client.getId();
        System.out.println("[Room:" + name + "] " + id + " joined");
        clients.put(id, client);
    }

    void dispatchToClients(String senderId, String message) {
        clients.values().forEach(client -> {
            if(!senderId.equals(client.getId())) {
                System.out.println("[Room:" + name + "] sender:'" + senderId + "', dest:'" + client.getId() + "', message=<" + message + ">");
                client.receive(name, message);
            }
        });
    }

    boolean hasJoined(String id) {
        return clients.containsKey(checkNotNull(id, "null id"));
    }
}
