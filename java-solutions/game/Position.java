package mnk;

import mnk.exceptions.CellNotEmptyMoveException;
import mnk.exceptions.InvalidTurnMoveException;
import mnk.exceptions.OutOfBoundsMoveException;

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
