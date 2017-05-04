package smartrics.samples.chat_server.protocol;

import java.util.Optional;

import static smartrics.samples.chat_server.util.Validation.checkItHasntCR;
import static smartrics.samples.chat_server.util.Validation.checkNotNull;

public class Send extends RoomCommand {
    static final String INSTRUCTION = "send";

    private final String message;

    public Send(String roomName, String message) {
        super(checkNotNull(roomName, "null room"));
        this.message = checkItHasntCR(Optional.ofNullable(message).orElse("").trim());
    }

    @Override
    public String serialise() {
        return INSTRUCTION + " " + getRoomName() + " " + message + "\n";
    }

    public String getMessage() {
        return message;
    }
}
