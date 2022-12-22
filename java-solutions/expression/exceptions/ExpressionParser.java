package expression.exceptions;

import expression.*;
import expression.parser.*;

public class ExpressionParser implements TripleParser {
    @Override
    public TripleExpression parse(String expression)
            throws ParserArgumentExpectedException, ParserEOFException,
            ParserUnexpectedCharException, ParserConstantOverflowException {
        ExpressionParserImpl parser = new ExpressionParserImpl(expression, new CheckedOperatorFactory());
        return parser.parse();
    }
}
