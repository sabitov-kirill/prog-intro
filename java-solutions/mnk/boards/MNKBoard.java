package mnk.boards;

import mnk.*;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.EMPTY, '·',
            Cell.BLOCKED, ' '
    );

    protected Direction[][] directionPairs;
    protected Cell[][] cells;

    private final int width, height, inRow;
    private int cellsCount;
    private int filledCellsCount;
    private Cell turn = Cell.X;
    private final BoardPossition position;


    public MNKBoard(int width, int height, int inRow) {
        this.width = width;
        this.height = height;
        this.inRow = inRow;
        this.cellsCount = width * height;

        initialize(width, height);

        this.position = new BoardPossition(cells, width, height, this::getTurnCell);
    }

    void initialize(int width, int height) {
        directionPairs = new Direction[][]{
                { new Direction(0, 1),  new Direction(0,  -1) },
                { new Direction(1, 1),  new Direction(-1, -1) },
                { new Direction(1, 0),  new Direction(-1,  0) },
                { new Direction(1, -1), new Direction(-1,  1) },
        };

        cells = new Cell[height][width];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.EMPTY);
        }
    }

    void calculateCellsCount() {
        filledCellsCount = 0;
        cellsCount = 0;

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell == Cell.O || cell == Cell.X) {
                    filledCellsCount++;
                }

                if (cell != Cell.BLOCKED) {
                    cellsCount++;
                }
            }
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Cell getTurnCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!position.isMoveValid(move)) {
            return Result.LOSE;
        }

        cells[move.row()][move.column()] = move.value();
        filledCellsCount++;

        for (Direction[] directionPair : directionPairs) {
            int directionPoints = 1;
            for (Direction direction : directionPair) {
                directionPoints += countDirectionPoints(move.row(), move.column(), move.value(), direction);
            }

            if (directionPoints >= inRow) {
                return Result.WIN;
            }
        }

        turn = turn.next();
        if (filledCellsCount >= cellsCount) {
            return Result.DRAW;
        }
        return Result.UNKNOWN;
    }

    int countDirectionPoints(int row, int column, Cell cell, Direction direction) {
        int count = 0;
        for (int move_ind = 0; move_ind < inRow; move_ind++) {
            row += direction.y();
            column += direction.x();

            if (row < 0 || row >= height
                    || column < 0 || column >= width
                    || cells[row][column] != cell) {
                break;
            }
            count += 1;
        }

        return count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("   ");
        for (int c = 0; c < width; c++) {
            sb.append(String.format("%02d ", c));
        }
        sb.append("\n  ╭").append("──┬".repeat(width - 1)).append("──╮");

        for (int r = 0; r < height; r++) {
            sb.append("\n");
            sb.append(String.format("%02d", r)).append('│');
            for (int c = 0; c < width; c++) {
                sb.append(SYMBOLS.get(cells[r][c])).append(" │");
            }

            if (r != height - 1) {
                sb.append("\n  ├").append("──┼".repeat(width - 1)).append("──┤");
            } else {
                sb.append("\n  ╰").append("──┴".repeat(width - 1)).append("──╯");
            }
        }
        return sb.toString();
    }
}
