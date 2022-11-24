package mnk.exceptions;

public class OutOfBoundsMoveException extends InvalidMoveException {
    public OutOfBoundsMoveException(boolean isRow, int currentValue, int allowedRange) {
        super(String.format(
                "Passed %s %d, while allowed range: [0, %d)",
                isRow ? "row" : "column",
                currentValue,
                allowedRange
        ));
    }

    public OutOfBoundsMoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
