package game;

import game.exceptions.CellNotEmptyMoveException;
import game.exceptions.InvalidTurnMoveException;
import game.exceptions.OutOfBoundsMoveException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Position {
    Cell getCell(int r, int c);
    boolean isMoveValid(Move move);
    void validateMove(Move move) throws
            IndexOutOfBoundsException, OutOfBoundsMoveException,
            CellNotEmptyMoveException, InvalidTurnMoveException;
}
