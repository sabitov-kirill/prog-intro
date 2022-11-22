package mnk.players;

import mnk.Cell;
import mnk.Move;
import mnk.Position;

public class ExceptionPlayer implements Player {
    @Override
    public Move createMove(final Position position, final Cell turnCell) {
        throw new IllegalStateException();
    }
}
