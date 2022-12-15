package expression.parser;

import expression.*;

import static expression.parser.PriorityLevel.OPERATORS_BY_PRIOR;

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
            // Unary priority levels parsed separated
            // (1, isUnary) -> (2, isUnary) -> ... -> (MAX_PRIOR, true)
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
            for (String opSign : OPERATORS_BY_PRIOR.get(MAX_PRIOR).operatorsSigns()) {
                if (take(opSign)) {
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
