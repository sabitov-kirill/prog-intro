package expression.parser;

public class ParserConstantOverflowException extends ParserException {
    public ParserConstantOverflowException(String overflowedNumber, String expression) {
        super("Constant is out of bounds " + overflowedNumber, expression);
    }
}
