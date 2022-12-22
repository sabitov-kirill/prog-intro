package expression.parser;

public class ParserUnexpectedCharException extends ParserException {
    public ParserUnexpectedCharException(String expected, String got, int charPosition, String expression) {
        super("Expected char " + expected + ", got " + got + " at char position " + charPosition, expression);
    }
}
