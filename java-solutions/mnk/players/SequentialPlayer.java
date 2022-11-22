package mnk.players;

import mnk.Cell;
import mnk.Move;
import mnk.Position;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class SequentialPlayer implements Player {
    private final int boundX, boundY;

    public SequentialPlayer(int boundX, int boundY) {
        this.boundX = boundX;
        this.boundY = boundY;
    }

    @Override
    public Move createMove(final Position position, final Cell turnCell) {
        for (int r = 0; r < boundY; r++) {
            for (int c = 0; c < boundX; c++) {
                final Move move = new Move(r, c, turnCell);
                if (position.isMoveValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
