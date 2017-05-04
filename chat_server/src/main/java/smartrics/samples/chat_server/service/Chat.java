package smartrics.samples.chat_server.service;

import smartrics.samples.chat_server.server.Client;
import smartrics.samples.chat_server.protocol.*;

import java.util.concurrent.ConcurrentHashMap;

import static smartrics.samples.chat_server.util.Validation.checkNotNull;

/**
 * Manager of chat rooms
 */
public class Chat {
    private final RoomFactory factory;
    private ConcurrentHashMap<String, Room> rooms;

    public Chat() {
        this(new SimpleRoomFactory());
    }

    public Chat(RoomFactory factory) {
        this.factory = factory;
        this.rooms = new ConcurrentHashMap<>();
    }

    public OpResult execute(Client client, Command command) {
        try {
            if (command instanceof Join) {
                Room r = rooms.get(command.getRoomName());
                if (r != null) {
                    if (!r.hasClient(client.getId())) {
                        r.join(client);
                    }
                } else {
                    Room newRoom = factory.newRoom(command.getRoomName());
                    newRoom.join(client);
                    rooms.put(command.getRoomName(), newRoom);
                    return OpResult.OK;
                }
            } else if (command instanceof Send) {
                Room r = rooms.get(command.getRoomName());
                Send send = (Send) command;
                r.dispatchToClients(client.getId(), send.getMessage());
                return OpResult.OK;
            }
            // ignore otherwise
            return OpResult.error("Unknown command");
        } catch (Exception e) {
            return OpResult.error("Unable to handle command: " + e.getMessage());
        }
    }

    public boolean hasRoom(String roomName) {
        return rooms.containsKey(checkNotNull(roomName, "null room name"));
    }
}
