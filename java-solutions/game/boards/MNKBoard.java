package game.boards;

import game.*;
import game.exceptions.CellNotEmptyMoveException;
import game.exceptions.InvalidTurnMoveException;
import game.exceptions.OutOfBoundsMoveException;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board {
    private class BoardPosition implements Position {
        @Override
        public Cell getCell(int r, int c) {
            return cells[r][c];
        }

        private boolean isRowOutOfBoundMove(Move move) { return move.row() < 0 || move.row() >= height; }
        private boolean isColumnOutOfBoundMove(Move move) { return move.column() < 0 || move.column() >= width; }
        private boolean isCellNotEmptyMove(Move move) { return getCell(move.row(), move.column()) != Cell.EMPTY; }
        private boolean isInvalidTurnMove(Move move) { return getTurnCell() != move.value(); }

        @Override
        public boolean isMoveValid(Move move) {
            return !isRowOutOfBoundMove(move)
                    && !isColumnOutOfBoundMove(move)
                    && !isCellNotEmptyMove(move)
                    && !isInvalidTurnMove(move);
        }

        @Override
        public void validateMove(final Move move)
                throws OutOfBoundsMoveException, CellNotEmptyMoveException, InvalidTurnMoveException {
            if (isRowOutOfBoundMove(move)) {
                throw new OutOfBoundsMoveException(
                        true, move.row(), height
                );
            }

            if (isColumnOutOfBoundMove(move)) {
                throw new OutOfBoundsMoveException(
                        false, move.column(), width
                );
            }

            if (isCellNotEmptyMove(move)) {
                throw new CellNotEmptyMoveException(move.row(), move.column());
            }

            if (isInvalidTurnMove(move)) {
                throw new InvalidTurnMoveException(move.value(), getTurnCell());
            }
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

    private static final int ADDITIONAL_MOVE_THR = 4;
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
    private final BoardPosition position = new BoardPosition();


    public MNKBoard(int width, int height, int inRow) {
        this.width = width;
        this.height = height;
        this.inRow = inRow;
        this.cellsCount = width * height;

        initialize(width, height);
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
            } else if (directionPoints >= ADDITIONAL_MOVE_THR && filledCellsCount < cellsCount) {
                return Result.ADDITIONAL_MOVE;
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
        return position.toString();
    }
}
