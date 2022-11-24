package game.players;

import game.Cell;
import game.Move;
import game.Position;
import game.exceptions.InvalidMoveException;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move createMove(final Position position, final Cell turnCell) {
        out.println("Position");
        out.println(position);

        while (true) {
            out.print(turnCell + "'s move: " + "Enter row and column: ");

            String r = in.next();
            String c = in.next();

            try {
                final Move move = new Move(Integer.parseInt(r), Integer.parseInt(c), turnCell);
                position.validateMove(move);
                return move;
            } catch (NumberFormatException e) {
                out.println("Input invalid: " + e.getMessage());
            } catch (InvalidMoveException e) {
                out.println("Move invalid: " + e.getMessage());
            }
        }
    }
}
