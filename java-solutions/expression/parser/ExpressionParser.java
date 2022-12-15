package expression.parser;

import expression.*;
import static expression.OperatorFactory.OPERATORS_BY_PRIOR;


import java.util.List;
import java.util.Set;

public class ExpressionParser implements TripleParser {
    @Override
    public TripleExpression parse(String expression) {
        ParserImpl parser = new ParserImpl(expression);
        return parser.parseTerm(0);
    }

    private static class ParserImpl extends BaseParser {
        private static final int MAX_PRIOR = OPERATORS_BY_PRIOR.size() - 1;

        public ParserImpl(CharSource source) {
            super(source);
        }

        public ParserImpl(String source) {
            this(new StringSource(source));
        }

        public CommonExpression parseTerm(int priority) {
            // (1, false) -> (2, false) -> ... -> (MAX_PRIOR, true)
            // Unary operations are considered to have the lowest priority and are always postfix
            if (priority == MAX_PRIOR) {
                return parseUnary();
            }

            // Increase priority (recursion level) to get left operand
            CommonExpression result = parseTerm(priority + 1);
            String currentOperatorSign;

            // Keep taking operations with same priority
            do {
                skipWhitespace();

                currentOperatorSign = "";
                for (String opSign : OPERATORS_BY_PRIOR.get(priority)) {
                    if (test(opSign.charAt(0))) {
                        expect(opSign);
                        currentOperatorSign = opSign;
                        break;
                    }
                }

                if (!currentOperatorSign.isEmpty()) {
                    CommonExpression right = parseTerm(priority + 1);
                    result = OperatorFactory.getBinaryOperator(currentOperatorSign, result, right);
                }
            } while (!currentOperatorSign.isEmpty());

            return result;
        }

        private CommonExpression parseUnary() {
            skipWhitespace();

            if (take('(')) {
                CommonExpression expression = parseTerm(0);
                expect(')');
                return expression;
            }

            boolean negatedConst = false;
            for (String opSign : OPERATORS_BY_PRIOR.get(MAX_PRIOR)) {
                if (test(opSign.charAt(0))) {
                    expect(opSign);

                    if (opSign.equals("-") && between('0', '9')) {
                        negatedConst = true;
                        break;
                    }

                    return OperatorFactory.getUnaryOperator(opSign, parseUnary());
                }
            }

            if (testVariable()) {
                return new Variable(String.valueOf(take()));
            }
            return takeConst(negatedConst);

        }

        private boolean testVariable() {
            return test('x') || test('y') || test('z');
        }

        private Const takeConst(boolean isNegated) {
            StringBuilder integerBuilder = new StringBuilder();
            if (isNegated) {
                integerBuilder.append('-');
            }

            skipWhitespace();
            takeInteger(integerBuilder);
            try {
                return new Const(Integer.parseInt(integerBuilder.toString()));
            } catch (NumberFormatException e) {
                throw error("Invalid number '" + integerBuilder + "'");
            }
        }
    }
}
