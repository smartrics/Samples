package smartrics.samples.chat_server.server.online;

import smartrics.samples.chat_server.protocol.Join;
import smartrics.samples.chat_server.protocol.ProtocolParser;
import smartrics.samples.chat_server.protocol.Send;
import smartrics.samples.chat_server.service.Chat;
import smartrics.samples.chat_server.ui.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerLauncher {

    public static void main(String[] args) throws Exception {
        Chat chat = new Chat();
        ProtocolParser parser = new ProtocolParser();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<?> future = executorService.submit(new OnlineServer("localhost", 8090, chat, parser));
        System.out.println("[Main] Wait for server to exit!");
        future.get();
    }
}
