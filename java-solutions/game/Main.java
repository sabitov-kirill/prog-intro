package game;

import game.boards.*;
import game.players.*;

public class Main {
    public static void main(String[] args) {
        ArgumentsParser argumentsParser = new ArgumentsParser(args);
        boolean logGame = argumentsParser.getOrDefault("log", false);
        int boardWidth = argumentsParser.getOrDefault("width", 3);
        int boardHeight = argumentsParser.getOrDefault("height", 3);
        int boardInRow = argumentsParser.getOrDefault("inRow", 3);
        int neededWins = argumentsParser.getOrDefault("neededWins", 1);

        Player[] players = {
                new HumanPlayer(),
                // new ExceptionPlayer(),
                // new CheatingPlayer(boardWidth, boardHeight),
                // new CheatingPlayer(boardWidth, boardWidth),
                // new SequentialPlayer(boardWidth, boardHeight),
                // new SequentialPlayer(boardWidth, boardWidth),
                // new RandomPlayer(boardWidth, boardWidth)
                // new RandomPlayer(boardWidth, boardWidth),
                new RandomPlayer(boardWidth, boardWidth),
        };

        int winsCount = 0, iteration = 0;
        while (Math.abs(winsCount) < neededWins) {
            final Game game = new Game(logGame, players[iteration % 2], players[(iteration + 1) % 2]);

            System.out.printf("Game #%d on board %dx%d (%d in row) starting...\n",
                    iteration, boardWidth, boardHeight, boardInRow);
            // int result = game.play(new HexagonBoard(boardWidth, boardInRow));
            int result = game.play(new MNKBoard(boardWidth, boardHeight, boardInRow));
            switch (result) {
                case 1 -> {
                    System.out.println("First player wins round");
                    winsCount += 1;
                }
                case 2 -> {
                    System.out.println("Second player wins won");
                    winsCount -= 1;
                }
                case 0 -> System.out.println("Round draw");
                default -> throw new AssertionError("Unknown result " + result);
            }
        };

        System.out.println(winsCount > 0 ? "First player wins" : "Second player wins");
    }
}
