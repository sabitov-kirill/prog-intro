package expression.parser;

public class ParserArgumentExpectedException extends ParserException {

    ParserArgumentExpectedException(int argumentPosition, String expression) {
        super("Expected argument",
                new StringBuilder(expression).insert(argumentPosition - 1, " -> ___ <- ").toString());
    }
}
