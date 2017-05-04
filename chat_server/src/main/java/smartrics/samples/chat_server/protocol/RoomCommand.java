package smartrics.samples.chat_server.protocol;

import static smartrics.samples.chat_server.util.Validation.checkNotNull;

public abstract class RoomCommand implements Command {

    private final String roomName;

    protected RoomCommand(String roomName) {
        this.roomName = checkNotNull(roomName, "null room");
    }

    public String getRoomName() {
        return roomName;
    }
}
