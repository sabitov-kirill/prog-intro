package mnk.exceptions;

public class OutOfBoundsMoveException extends InvalidMoveException {
    public OutOfBoundsMoveException(String message) {
        super(message);
    }

    public OutOfBoundsMoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
