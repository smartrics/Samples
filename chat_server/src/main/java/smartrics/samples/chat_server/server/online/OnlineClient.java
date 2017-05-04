package smartrics.samples.chat_server.server.online;

import smartrics.samples.chat_server.protocol.Command;
import smartrics.samples.chat_server.protocol.ProtocolParser;
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

public class OnlineClient implements Client {
    private SocketChannel socketChannel;
    private List<Receiver> receiverList;

    public OnlineClient(ExecutorService executorService, ProtocolParser parser, String host, int port) {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to connect");
        }
        this.receiverList = new ArrayList<>();
        executorService.submit(() -> {
            int nBytes = 0;
            StringBuffer result = new StringBuffer();
            ByteBuffer buf = ByteBuffer.allocate(2048);
            try {
                while ((nBytes = socketChannel.read(buf)) > 0) {
                    buf.flip();
                    Charset charset = Charset.forName("us-ascii");
                    CharsetDecoder decoder = charset.newDecoder();
                    CharBuffer charBuffer = decoder.decode(buf);
                    final String str = charBuffer.toString();
                    result.append(str);
                    buf.flip();
                    boolean found = true;
                    String s = result.toString();
                    while(found) {
                        int pos = s.indexOf("\n");
                        if(pos >= 0) {
                            String m = s.substring(0, pos);
                            Command command = parser.parse(m);
                            if (command instanceof Send) {
                                Send send = (Send) command;
                                receive(send.getRoomName(), send.getMessage());
                            }
                            if(pos < s.length()) {
                                s = s.substring(pos).trim();
                            }
                        } else {
                            found = false;
                            result = new StringBuffer(s);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        });
    }

    @Override
    public String getId() {
        try {
            return socketChannel.getLocalAddress().toString();
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
        receiverList.forEach(receiver -> receiver.onNewMessage(room, message));
    }

    @Override
    public void addReceiver(Receiver receiver) {
        receiverList.add(receiver);
    }

    public void stopClient() throws IOException, InterruptedException {
        socketChannel.close();
    }

}
