package smartrics.samples.chat_server.util;


public class Validation {
    public static <T> T checkNotNull(T value, String message) {
        if(value == null) {
            throw new NullPointerException(message);
        }
        return value;
    }

    public static String checkItHasntCR(String s) {
        if(s.contains("\n")) {
            throw new IllegalArgumentException("Invalid message - it contains CR");
        }
        return s;
    }
}
