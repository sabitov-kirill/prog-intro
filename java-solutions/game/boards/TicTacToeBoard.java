package mnk.boards;

import mnk.*;
import mnk.exceptions.InvalidMoveException;
import mnk.exceptions.CellNotEmptyMoveException;
import mnk.exceptions.InvalidTurnMoveException;
import mnk.exceptions.OutOfBoundsMoveException;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard extends MNKBoard {
    public TicTacToeBoard() {
        super(3, 3, 3);
    }
}
