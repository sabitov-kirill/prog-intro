package mnk.players;

import mnk.Cell;
import mnk.Move;
import mnk.Position;

import java.util.Random;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class RandomPlayer implements Player {
    private final Random random;
    private final int boundX, boundY;

    public RandomPlayer(final Random random, int boundX, int boundY) {
        this.random = random;
        this.boundX = boundX;
        this.boundY = boundY;
    }

    public RandomPlayer(int boundX, int boundY) {
        this(new Random(), boundX, boundY);
    }

    @Override
    public Move createMove(final Position position, final Cell turnCell) {
        while (true) {
            int r = random.nextInt(boundY);
            int c = random.nextInt(boundX);
            final Move move = new Move(r, c, turnCell);
            if (position.isMoveValid(move)) {
                return move;
            }
        }
    }
}
