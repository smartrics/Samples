package smartrics.samples.chat_server.protocol;

public interface Command {
    String getRoomName();

    String serialise();

}
