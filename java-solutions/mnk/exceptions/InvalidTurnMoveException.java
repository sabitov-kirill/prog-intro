package mnk.exceptions;

public class InvalidTurnMoveException extends InvalidMoveException {
    public InvalidTurnMoveException(String message) {
        super(message);
    }

    public InvalidTurnMoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
