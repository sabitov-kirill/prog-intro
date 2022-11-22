package mnk;

import mnk.boards.*;
import mnk.players.*;

public class Main {
    public static void main(String[] args) {
        ArgumentsParser argumentsParser = new ArgumentsParser(args);
        int boardWidth = argumentsParser.getOrDefault("width", 3);
        int boardHeight = argumentsParser.getOrDefault("height", 3);
        int boardInRow = argumentsParser.getOrDefault("inRow", 3);

        final Game game = new Game(
                true,
                new RandomPlayer(boardWidth, boardHeight),
                new RandomPlayer(boardWidth, boardHeight)
        );

        int result = game.play(new HexagonBoard(boardWidth, boardInRow));
        switch (result) {
            case 1 -> System.out.println("First player won");
            case 2 -> System.out.println("Second player won");
            case 0 -> System.out.println("Draw");
            default -> throw new AssertionError("Unknown result " + result);
        }
    }
}
