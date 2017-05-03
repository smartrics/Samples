package smartrics.samples.chat_server;

import java.util.Optional;

import static smartrics.samples.chat_server.Validation.checkItHasntCR;
import static smartrics.samples.chat_server.Validation.checkNotNull;


public interface Protocol {

    interface Command {
        String getRoomName();

        String serialise();
    }

    class Join implements Command {
        private final String roomName;

        public Join(String roomName) {
            this.roomName = checkNotNull(roomName, "null room");
        }

        @Override
        public String getRoomName() {
            return roomName;
        }

        @Override
        public String serialise() {
            return "join " + roomName;
        }
    }

    class Send implements Command {
        private final String roomName;
        private final String message;

        public Send(String roomName, String message) {
            this.roomName = checkNotNull(roomName, "null room").trim();
            this.message = checkItHasntCR(Optional.ofNullable(message).orElse("").trim());
        }

        @Override
        public String getRoomName() {
            return roomName;
        }

        @Override
        public String serialise() {
            return "send " + roomName + " " + message;
        }

        public String getMessage() {
            return message;
        }
    }

    OpResult execute(Command command);

}
