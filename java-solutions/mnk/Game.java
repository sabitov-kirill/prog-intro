package mnk;

import mnk.boards.Board;
import mnk.players.Player;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Game {
    private final static int PLAYERS_COUNT = 2;

    private final boolean log;
    private final Player[] players = new Player[PLAYERS_COUNT];

    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.players[0] = player1;
        this.players[1] = player2;
    }

    public int play(Board board) {
        while (true) {
            for (int player_no = 0; player_no < PLAYERS_COUNT; player_no++) {
                final int result1 = move(board, players[player_no], player_no + 1);
                if (result1 != -1) {
                    return result1;
                }
            }
        }
    }

    private int move(final Board board, final Player player, final int no) {
        Move playerMove;
        try {
            playerMove = player.createMove(board.getPosition(), board.getTurnCell());
        } catch (Throwable e) {
            log("Player " + no + " thrown exception and lose: " + e.getMessage());
            return 3 - no;
        }

        final Result result = board.makeMove(playerMove);
        log("Player " + no + " move: " + playerMove);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return 3 - no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println("[log] " + message);
        }
    }
}
