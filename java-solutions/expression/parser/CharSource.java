package expression.parser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface CharSource {
    boolean hasNext();
    char next();
    void setAnchor();
    void returnToAnchor();
    IllegalArgumentException error(String message);
}
