package game.boards;

import game.Cell;
import game.Move;
import game.Position;
import game.Result;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Board {
    Position getPosition();
    Cell getTurnCell();
    Result makeMove(Move move);
}
