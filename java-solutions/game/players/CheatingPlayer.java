package mnk.players;

import mnk.Cell;
import mnk.Move;
import mnk.Position;
import mnk.boards.MNKBoard;

public class CheatingPlayer implements Player {
    private final int boundX, boundY;

    public CheatingPlayer(int boundX, int boundY) {
        this.boundX = boundX;
        this.boundY = boundY;
    }

    @Override
    public Move createMove(final Position position, final Cell turnCell) {
        final MNKBoard board = (MNKBoard) position;
        Move first = null;

        for (int r = 0; r < boundY; r++) {
            for (int c = 0; c < boundX; c++) {
                final Move move = new Move(r, c, board.getTurnCell());
                if (position.isMoveValid(move)) {
                    if (first == null) {
                        first = move;
                    } else {
                        board.makeMove(move);
                    }
                }
            }
        }
        return first;
    }
}
