package smartrics.samples.chat_server.server.online;

import smartrics.samples.chat_server.protocol.Join;
import smartrics.samples.chat_server.protocol.ProtocolParser;
import smartrics.samples.chat_server.protocol.Send;
import smartrics.samples.chat_server.ui.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClientPerfLauncher {

    public static final int MAX = 100;

    public static void main(String[] args) throws Exception {
        ProtocolParser parser = new ProtocolParser();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<User> users = new ArrayList<>();
        List<Future> results = new ArrayList<>();
        for (int cl = 0; cl < MAX; cl++) {
            OnlineClient client = new OnlineClient(executorService, parser, "localhost", 8090);
            User user = new User(client);
            users.add(user);
            for (int room = 0; room < MAX; room++) {
                final String roomName = "foo" + room;
                results.add(executorService.submit(() -> user.doCommand(new Join(roomName))));
                Thread.sleep(10);
            }
        }

        waitForCompletion(results);

        users.stream().forEach(user -> {
            for(int room = 0; room < 20; room++) {
                final String roomName = "foo" + room;
                results.add(executorService.submit(() -> user.doCommand(new Send(roomName, "user sends message"))));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        waitForCompletion(results);

        long count = users.stream().map(User::getCount).mapToLong(i -> i).sum();
        System.out.println("COUNT: " + count);
    }

    private static void waitForCompletion(List<Future> results) {
        results.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        results.clear();
    }
}
