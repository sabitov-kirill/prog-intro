package mnk.boards;

import mnk.Cell;
import mnk.Move;
import mnk.Position;
import mnk.Result;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Board {
    Position getPosition();
    Cell getTurnCell();
    Result makeMove(Move move);
}
