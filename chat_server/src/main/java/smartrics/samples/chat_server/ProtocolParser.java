package smartrics.samples.chat_server;

import static smartrics.samples.chat_server.Validation.checkNotNull;


public class ProtocolParser {

    public Protocol.Command parse(String line) {
        String newLine = checkNotNull(line, "null line").trim();
        int commandPos = newLine.indexOf(" ");
        if(commandPos == -1) {
            throw new IllegalArgumentException("Invalid line - must contain something after the command: " + line);
        }
        String command = newLine.substring(0, commandPos);
        String rest = newLine.substring(commandPos).trim();

        if("join".equals(command)) {
            return new Protocol.Join(rest);
        }
        if("send".equals(command)) {
            String mr = rest;
            commandPos = mr.indexOf(" ");
            String room = mr;
            String message = "";
            if(commandPos > 0) {
                room = mr.substring(0, commandPos);
                message = mr.substring(commandPos).trim();
            }
            return new Protocol.Send(room, message);
        }
        throw new IllegalArgumentException("Invalid line - unknown command: " + line);
    }

}
