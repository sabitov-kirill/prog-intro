package game.players;

import game.Cell;
import game.Move;
import game.Position;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Player {
    Move createMove(Position position, Cell turnCell);
}
