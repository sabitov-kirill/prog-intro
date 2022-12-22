package expression.exceptions;

import expression.*;
import expression.parser.ParserArgumentExpectedException;
import expression.parser.ParserEOFException;
import expression.parser.ExpressionParserImpl;
import expression.parser.ParserUnexpectedCharException;

public class ExpressionParser implements TripleParser {
    @Override
    public TripleExpression parse(String expression)
            throws ParserArgumentExpectedException, ParserEOFException, ParserUnexpectedCharException {
        ExpressionParserImpl parser = new ExpressionParserImpl(expression, new CheckedOperatorFactory());
        return parser.parse();
    }
}
