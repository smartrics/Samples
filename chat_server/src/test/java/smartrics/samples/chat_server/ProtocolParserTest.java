package smartrics.samples.chat_server;

import org.junit.Before;
import org.junit.Test;
import smartrics.samples.chat_server.protocol.Command;
import smartrics.samples.chat_server.protocol.ProtocolParser;
import smartrics.samples.chat_server.protocol.Send;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class ProtocolParserTest {

    private ProtocolParser parser;

    @Before
    public void setup() {
        parser = new ProtocolParser();
    }

    @Test
    public void parseJoin() {
        Command command = parser.parse("join foo");

        assertThat(command.getClass().getSimpleName(), is("Join"));
        assertThat(command.getRoomName(), is("foo"));
    }
    @Test
    public void parseSend() {
        Command command = parser.parse("send foo bar");

        assertThat(command.getClass().getSimpleName(), is("Send"));
        assertThat(command.getRoomName(), is("foo"));
        assertThat(((Send)command).getMessage(), is("bar"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void commandWithoutArgThrowsIAE() {
        parser.parse("send");
    }

    @Test(expected = IllegalArgumentException.class)
    public void unknownCommandThrowsIAE() {
        parser.parse("unknown foo bar");
    }

    @Test
    public void sendWithoutMessageHasEmptyMessage() {
        Command command = parser.parse("send foo");
        assertThat(((Send)command).getMessage(), is(""));
    }
}