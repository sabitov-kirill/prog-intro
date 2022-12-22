package expression.parser;

public class ParserEOFException extends ParserException {
    ParserEOFException(int charPosition, boolean needed, String expression) {
        super((needed ? "Ex" : "Une") + "pected EOF at char position (" + charPosition + ")", expression);
    }
}
