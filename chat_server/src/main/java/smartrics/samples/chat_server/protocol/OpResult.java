package smartrics.samples.chat_server.protocol;

import java.util.Optional;

import static smartrics.samples.chat_server.util.Validation.checkNotNull;

public class OpResult {

    public static final OpResult OK = new OpResult(Code.OK);

    private final Code code;
    private final String message;

    public OpResult(Code code) {
        this(code, null);
    }

    public OpResult(Code code, String message) {
        this.code = checkNotNull(code, "null code");
        this.message = message;
    }

    public static OpResult error(String s) {
        return new OpResult(Code.ERROR, s);
    }

    public enum Code  {

        OK(1), ERROR(-1);

        private final int code;

        Code(int code) {
            this.code = code;
        }

    }

    public Code getCode() {
        return code;
    }

    public Optional<String> getMessage() {
        return java.util.Optional.of(message);
    }

}
