# Chat server

The task is to construct a chat server that will allow multiple clients to connect to it, join one or more rooms, and send and receive messages from those rooms. (Think: like a very basic IRC server)

The basic functionality is

* Clients can join one or more rooms
* When a client joins a room that does not already exist, a new room is created
* Clients can send messages to rooms that they have joined
* A message sent to a room gets distributed to all the clients that have joined the room

## Protocol

Clients should interact with the server by opening a TCP connection and using a very simple text based protocol

All messages are ASCII strings

\n is a new line character

<room_name> and <message> are ASCII strings that cannot be empty or contain the newline character

The protocol is as follows:

### JOIN

To join a room

JOIN <room_name>\n

### SEND

To send a value to a room

SEND <room_name> <message>\n

## Important notes

* The server needs to scale to 100 concurrent connections and 100 rooms. Choose the io packages you use *appropriately* for that.
* Please use *only* standard J2SE classes (i.e. from packages java.*), no other third party dependencies. The only exception is the testing tool as mentioned below.
* Please provide client side tests - you can use any popular test tool (e.g. JUnit) for this
* You can use any common build tool to build your project (e.g. Maven, Gradle, ant)
* Feel free to browse the web. Please do not copy code from the web or books though!
* Please push your completed project to GitHub
* Please provide a simple README for the project
* Don't worry if you can't get it all complete in the time, but it's better to submit something that partially works than nothing at all.

Good luck!

