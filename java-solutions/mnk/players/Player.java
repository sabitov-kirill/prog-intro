package mnk.players;

import mnk.Cell;
import mnk.Move;
import mnk.Position;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Player {
    Move createMove(Position position, Cell turnCell);
}
