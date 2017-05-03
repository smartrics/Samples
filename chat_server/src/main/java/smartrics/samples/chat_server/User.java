package smartrics.samples.chat_server;

public class User implements Protocol, Receiver {

    private final Client client;

    public User(Client client) {
        this.client = client;
        this.client.addReceiver(this);
    }

    @Override
    public OpResult execute(Command command) {
        try {
            client.dispatch(command.serialise());
            return new OpResult(OpResult.Code.OK);
        } catch (Exception e) {
            return new OpResult(OpResult.Code.ERROR, e.getMessage());
        }
    }


    @Override
    public void newMessage(String room, String message) {
        System.out.println(client.getId() + "@" + room + ": " + message);
    }
}
