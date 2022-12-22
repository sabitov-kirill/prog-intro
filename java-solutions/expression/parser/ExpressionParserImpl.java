package expression.parser;

import expression.*;

import static expression.parser.PriorityLevel.OPERATORS_BY_PRIOR;


// :NOTE:  Expected argument at argument position (3) (for expression f=1 + (x * y - ) + 3)
// непонятно, что такое позиция, особенно если закинуть побольше унарных минусов

// :NOTE:  Expected argument at argument position (0) (for expression f=-2147483649)
// вполне себе аргумент, здесь должно быть другое сообщение
public class ExpressionParserImpl extends BaseParser {
    private static final int MAX_PRIOR = OPERATORS_BY_PRIOR.size() - 1;
    private final OperatorFactory operatorFactory;
    private int currentTokenPosition = 0;

    public ExpressionParserImpl(String source, OperatorFactory operatorFactory) {
        super(source);
        this.operatorFactory = operatorFactory;
    }

    public CommonExpression parse()
            throws ParserArgumentExpectedException, ParserEOFException,
            ParserUnexpectedCharException, ParserConstantOverflowException {
        CommonExpression expression = parseTerm(0);
        expectEof();
        return expression;
    }

    public CommonExpression parseTerm(int priority)
            throws ParserArgumentExpectedException, ParserUnexpectedCharException, ParserConstantOverflowException {
        // Unary priority levels parsed separated
        // (1, isUnary) -> (2, isUnary) -> ... -> (MAX_PRIOR, true)

        // Term(n) := Variable | Number
        //     | Term(n - 1)     <binary operator with priority n> <whitespace> Term(n - 1)
        //     | '(' Term(n) ')' <binary operator with priority n> '(' Term(n) ')'
        //     | <unary operator with priority n> Term(n - 1)
        //     | <unary operator with priority n> '(' Term(n) ')'
        //     | [<whitespace>+] Term(n) [<whitespace>+];

        // Variable := 'x' | 'y' | 'z';

        // Number := <'1' to '9'> <'0' to '9'>+
        //     | '-' Number
        //     | Number <whitespace>+;
        if (priority == MAX_PRIOR || OPERATORS_BY_PRIOR.get(priority).isUnary()) {
            return parseUnary();
        }

        // Increase priority (recursion level) to get left operand
        CommonExpression result = parseTerm(priority + 1);
        String currentOperatorSign;

        // Keep taking operations with same priority
        do {
            skipWhitespace();

            currentOperatorSign = "";
            for (String opSign : OPERATORS_BY_PRIOR.get(priority).operatorsSigns()) {
                if (take(opSign)) {
                    currentOperatorSign = opSign;
                    break;
                }
            }

            if (!currentOperatorSign.isEmpty()) {
                expectOperatorSpacing(currentOperatorSign);
                CommonExpression right = parseTerm(priority + 1);
                result = operatorFactory.getBinaryOperator(currentOperatorSign, result, right);
            }
        } while (!currentOperatorSign.isEmpty());

        return result;
    }

    private CommonExpression parseUnary()
            throws ParserArgumentExpectedException, ParserUnexpectedCharException, ParserConstantOverflowException {
        skipWhitespace();

        if (take('(')) {
            CommonExpression expression = parseTerm(0);
            expect(')');
            return expression;
        }

        boolean negatedConst = false;
        for (String opSign : OPERATORS_BY_PRIOR.get(MAX_PRIOR).operatorsSigns()) {
            if (take(opSign)) {
                if (opSign.equals("-") && between('0', '9')) {
                    negatedConst = true;
                    break;
                }

                expectOperatorSpacing(opSign);
                return operatorFactory.getUnaryOperator(opSign, parseUnary());
            }
        }

        CommonExpression expression = testVariable() ? takeVariable() : takeConst(negatedConst);

        currentTokenPosition++;
        return expression;

    }

    private boolean testVariable() {
        return test('x') || test('y') || test('z');
    }

    private Variable takeVariable() {
        return new Variable(String.valueOf(take()));
    }

    private Const takeConst(boolean isNegated) throws ParserArgumentExpectedException, ParserConstantOverflowException {
        StringBuilder integerBuilder = new StringBuilder();
        if (isNegated) {
            integerBuilder.append('-');
        }

        skipWhitespace();
        takeInteger(integerBuilder);
        if (integerBuilder.isEmpty()) {
            throw new ParserArgumentExpectedException(source.getPosition(), sourceData);
        }

        try {
            return new Const(Integer.parseInt(integerBuilder.toString()));
        } catch (NumberFormatException e) {
            throw new ParserConstantOverflowException(integerBuilder.toString(), sourceData);
        }
    }

    private void expectEof() throws ParserEOFException {
        if (!eof()) {
            throw new ParserEOFException(source.getPosition(), true, sourceData);
        }
    }

    private void expectOperatorSpacing(String operatorSign) throws ParserUnexpectedCharException {
        if (operatorSign.length() > 1 && !test('(') && !testOperatorsSign()) {
            expectWhitespace();
        }
    }

    private boolean testOperatorsSign() {
        for (int prior = 0; prior < MAX_PRIOR; prior++) {
            for (String opSign : OPERATORS_BY_PRIOR.get(prior).operatorsSigns()) {
                if (test(opSign)) {
                    return true;
                }
            }
        }
        return false;
    }
}
