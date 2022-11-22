package mnk;

import mnk.exceptions.*;

import java.util.function.Supplier;

public class BoardPossition implements Position {
    private final Cell[][] boardCells;
    private final int boardWidth;
    private final int boardHeight;
    private final Supplier<Cell> turnCellSuplier;

    public BoardPossition(Cell[][] cells, int boardWidth, int boardHeight, Supplier<Cell> turnSupplier) {
        this.boardCells = cells;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.turnCellSuplier = turnSupplier;
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return boardCells[r][c];
    }

    private boolean isRowOutOfBoundMove(Move move) { return move.row() < 0 || move.row() >= boardWidth; }
    private boolean isColumnOutOfBoundMove(Move move) { return move.column() < 0 || move.column() >= boardHeight; }
    private boolean isCellNotEmptyMove(Move move) { return getCell(move.row(), move.column()) != Cell.EMPTY; }
    private boolean isInvalidTurnMove(Move move) { return turnCellSuplier.get() != move.value(); }

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
            throw new OutOfBoundsMoveException(String.format(
                    "Passed row: %s, while allowed range: [0, %d)",
                    move.row(),
                    boardHeight
            ));
        }

        if (isColumnOutOfBoundMove(move)) {
            throw new OutOfBoundsMoveException(String.format(
                    "Passed column: %s, while allowed range: [0, %d)",
                    move.row(),
                    boardWidth
            ));
        }

        if (isCellNotEmptyMove(move)) {
            throw new CellNotEmptyMoveException("Cell of move " + move + " is not empty");
        }

        if (isInvalidTurnMove(move)) {
            throw new InvalidTurnMoveException(String.format(
                    "Made turn %s, while should be %s",
                    move.value(),
                    turnCellSuplier.get()
            ));
        }
    }
}
