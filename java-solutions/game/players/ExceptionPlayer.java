package game.players;

import game.Cell;
import game.Move;
import game.Position;

public class ExceptionPlayer implements Player {
    @Override
    public Move createMove(final Position position, final Cell turnCell) {
        throw new IllegalStateException();
    }
}
