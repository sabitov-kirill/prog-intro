package expression.parser;

public class ParserArgumentExpectedException extends ParserException {

    ParserArgumentExpectedException(int argumentPosition, String expression) {
        super("Expected argument at argument position (" + argumentPosition + ")", expression);
    }
}
