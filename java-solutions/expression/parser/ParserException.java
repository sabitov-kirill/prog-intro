package expression.parser;

public class ParserException extends Exception {
    public ParserException(String message, String expression) {
        super(message + " (for expression f=" + expression + ")");
    }
}
