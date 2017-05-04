package smartrics.samples.chat_server.protocol;


public interface Protocol {

    OpResult execute(Command command);

}
