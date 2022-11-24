package game.exceptions;

public class CellNotEmptyMoveException extends InvalidMoveException {
    public CellNotEmptyMoveException(int r, int c) {
        super(String.format(
                "Cell (%d, %d) in not empty", r, c
        ));
    }

    public CellNotEmptyMoveException(String message,Throwable cause) {
        super(message, cause);
    }
}
