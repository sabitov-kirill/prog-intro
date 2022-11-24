package mnk.boards;

import mnk.*;
import mnk.exceptions.CellNotEmptyMoveException;
import mnk.exceptions.InvalidTurnMoveException;
import mnk.exceptions.OutOfBoundsMoveException;

import java.util.Arrays;
import java.util.Map;

public class MNKBoardWithAdditionalMoves extends MNKBoard {


    public MNKBoardWithAdditionalMoves(int width, int height, int inRow) {
        super(width, height, inRow);
    }
}
