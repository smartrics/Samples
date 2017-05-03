package smartrics.samples.chat_server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class Chat {
    private ConcurrentHashMap<String, Room> rooms;

    public Chat() {
        rooms = new ConcurrentHashMap<>();
    }

    public OpResult execute(Client client, Protocol.Command command) {
        try {
            if (command instanceof Protocol.Join) {
                Room r = rooms.get(command.getRoomName());
                if (r != null) {
                    if (!r.hasClient(client.getId())) {
                        r.join(client);
                    }
                } else {
                    Room newRoom = new Room(command.getRoomName());
                    newRoom.join(client);
                    rooms.put(command.getRoomName(), newRoom);
                }
            } else if (command instanceof Protocol.Send) {
                Room r = rooms.get(command.getRoomName());
                Protocol.Send send = (Protocol.Send) command;
                r.dispatchToClients(client.getId(), send.getMessage());
            }
            // ignore otherwise
            return OpResult.error("Unknown command");
        } catch (Exception e) {
            return OpResult.error("Unable to handle command: " + e.getMessage());
        }
    }
}
