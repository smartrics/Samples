package smartrics.samples.chat_server.protocol;

public class Join extends RoomCommand {

    static final String INSTRUCTION = "join";

    public Join(String roomName) {
        super(roomName);
    }

    @Override
    public String serialise() {
        return INSTRUCTION + " " + getRoomName() + "\n";
    }
}
