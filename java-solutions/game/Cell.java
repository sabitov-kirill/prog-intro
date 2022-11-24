package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public enum Cell {
    X, O, EMPTY, BLOCKED;

    public Cell next() {
        return switch (this) {
            case O -> X;
            case X -> O;
            default -> this;
        };
    }
}
