package smartrics.samples.chat_server.server.online;

import smartrics.samples.chat_server.protocol.ProtocolParser;
import smartrics.samples.chat_server.server.offline.OfflineServer;
import smartrics.samples.chat_server.service.Chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class OnlineServer extends OfflineServer implements Runnable {
    private final int port;
    private ServerSocketChannel ssc;
    private Selector selector;
    private ByteBuffer buf = ByteBuffer.allocate(256);

    OnlineServer(String host, int port, Chat chat, ProtocolParser parser) throws IOException {
        super(chat, parser);
        this.port = port;
        this.ssc = ServerSocketChannel.open();
        this.ssc.socket().bind(new InetSocketAddress(host, port));
        this.ssc.configureBlocking(false);
        this.selector = Selector.open();

        this.ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        try {
            System.out.println("[OnlineServer] Starting on port " + this.port);

            Iterator<SelectionKey> iter;
            SelectionKey key;
            while (this.ssc.isOpen()) {
                selector.select();
                iter = this.selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    key = iter.next();
                    iter.remove();

                    if (key.isAcceptable()) {
                        this.handleAccept(key);
                    }
                    if (key.isReadable()) {
                        this.handleRead(key);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IOException, server of port " + this.port + " terminating. Stack trace:");
            e.printStackTrace();
        }
    }

    private void handleAccept(SelectionKey key) throws IOException {
        SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
        String address = (new StringBuilder(sc.socket().getInetAddress().toString())).append(":").append(sc.socket().getPort()).toString();
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ, address);
        System.out.println("[OnlineServer] accepted connection from: " + address);
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel ch = (SocketChannel) key.channel();
        StringBuilder sb = new StringBuilder();
        buf.clear();
        int read = 0;
        while ((read = ch.read(buf)) > 0) {
            buf.flip();
            byte[] bytes = new byte[buf.limit()];
            buf.get(bytes);
            sb.append(new String(bytes));
            buf.clear();
        }
        String msg;
        if (read < 0) {
            System.out.println(key.attachment() + " has disconnected!");
            ch.close();
            return;
        } else {
            msg = sb.toString();
        }
        super.receive(new ClientProxy(ch), msg);
    }
}