package game.exceptions;

import game.Cell;

public class InvalidTurnMoveException extends InvalidMoveException {
    public InvalidTurnMoveException(Cell currentCell, Cell turnCell) {
        super(String.format(
                "Made turn %s, while should be %s",
                currentCell,
                turnCell
        ));
    }

    public InvalidTurnMoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
