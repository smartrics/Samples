package smartrics.samples.chat_server.service;

public interface RoomFactory {
    default Room newRoom(String name) {
        return new Room(name);
    }
}
