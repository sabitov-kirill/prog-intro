package mnk.exceptions;

public class CellNotEmptyMoveException extends InvalidMoveException {
    public CellNotEmptyMoveException(String message) {
        super(message);
    }
    public CellNotEmptyMoveException(String message,Throwable cause) {
        super(message, cause);
    }
}
