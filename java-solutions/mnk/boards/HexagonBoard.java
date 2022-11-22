package mnk.boards;

import mnk.BoardPossition;
import mnk.Cell;
import mnk.Direction;

import java.util.Arrays;

public class HexagonBoard extends MNKBoard {
    public HexagonBoard(int size, int inRow) {
        super(
                size % 2 == 0 ? size - 1 : size,
                size % 2 == 0 ? size - 1 : size,
                inRow
        );
    }

    @Override
    void initialize(int width, int height) {
        directionPairs = new Direction[][]{
                { new Direction(1,  1),  new Direction(-1, -1) },
                { new Direction(1, -1),  new Direction(-1,  1) },
                { new Direction(1,  0),  new Direction(-1,  0) },
        };

        cells = new Cell[height][width];
        int start = height / 2, end = width / 2;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells[y][x] = x >= start && x <= end && (x + y) % 2 == (height / 2) % 2 ?
                        Cell.EMPTY : Cell.BLOCKED;
            }

            int diff = y < height / 2 ? 1 : -1;
            start -= diff;
            end += diff;
        }

        calculateCellsCount();
    }
}
