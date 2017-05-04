package smartrics.samples.chat_server.protocol;

import static smartrics.samples.chat_server.util.Validation.checkNotNull;


public class ProtocolParser {

    public Command parse(String line) {
        String newLine = checkNotNull(line, "null line").trim();
        int commandPos = newLine.indexOf(" ");
        if (commandPos == -1) {
            throw new IllegalArgumentException("Invalid line - must contain something after the command: " + line);
        }
        String command = newLine.substring(0, commandPos);
        String rest = newLine.substring(commandPos).trim();

        if (Join.INSTRUCTION.equals(command)) {
            return new Join(rest);
        }
        if (Send.INSTRUCTION.equals(command)) {
            commandPos = rest.indexOf(" ");
            String room = rest;
            String message = "";
            if (commandPos > 0) {
                room = rest.substring(0, commandPos);
                message = rest.substring(commandPos).trim();
            }
            return new Send(room, message);
        }
        throw new IllegalArgumentException("Invalid line - unknown command: " + line);
    }

}
