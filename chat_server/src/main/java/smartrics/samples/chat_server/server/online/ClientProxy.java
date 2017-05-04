package smartrics.samples.chat_server.server.online;

import smartrics.samples.chat_server.protocol.Send;
import smartrics.samples.chat_server.server.Client;
import smartrics.samples.chat_server.server.Receiver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ClientProxy implements Client {
    private SocketChannel socketChannel;

    public ClientProxy(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public String getId() {
        try {
            return socketChannel.getRemoteAddress().toString();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to find ID");
        }
    }

    @Override
    public void dispatch(String line) {
        ByteBuffer msgBuf = ByteBuffer.wrap(line.getBytes());
        try {
            socketChannel.write(msgBuf);
            msgBuf.rewind();
            msgBuf.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receive(String room, String message) {
        dispatch(new Send(room, message).serialise());
    }

    @Override
    public void addReceiver(Receiver receiver) {

    }

}
