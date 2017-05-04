package smartrics.samples.chat_server.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import smartrics.samples.chat_server.protocol.Join;
import smartrics.samples.chat_server.protocol.Send;
import smartrics.samples.chat_server.server.Client;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatTest {

    public static final String ROOM_NAME = "foo";
    @Mock
    private RoomFactory mockFactory;

    private Room room;

    private Chat chat;

    @Mock
    private Client mockClient1;

    @Mock
    private Client mockClient2;

    @Before
    public void setup() {
        room = new Room(ROOM_NAME);
        chat = new Chat(mockFactory);
        when(mockFactory.newRoom("foo")).thenReturn(room);
        when(mockClient1.getId()).thenReturn("1");
        when(mockClient2.getId()).thenReturn("2");
    }

    @Test
    public void whenClientJoinsANewRoom_thenRoomIsCreated() {
        chat.execute(mockClient1, new Join(ROOM_NAME));
        assertThat(chat.hasRoom(ROOM_NAME), is(true));
    }

    @Test
    public void whenClientJoinsARoom_thenClientIsJoined() {
        chat.execute(mockClient1, new Join(ROOM_NAME));
        assertThat(room.hasJoined("1"), is(true));
    }

    @Test
    public void whenClientSendsMessage_thenMessageDispatchedToAllOtherClientsInTheRoom() {
        chat.execute(mockClient1, new Join(ROOM_NAME));
        chat.execute(mockClient2, new Join(ROOM_NAME));
        chat.execute(mockClient1, new Send(ROOM_NAME, "bar_message"));

        verify(mockClient2).receive(ROOM_NAME, "bar_message");
        verify(mockClient1, times(0)).receive(ROOM_NAME, "bar_message");
    }

}