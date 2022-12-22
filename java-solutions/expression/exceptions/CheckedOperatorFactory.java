package expression.exceptions;

import expression.*;

public class CheckedOperatorFactory implements OperatorFactory {
    public AbstractBinaryOperator getBinaryOperator(String sign, CommonExpression left, CommonExpression right) {
        return switch (sign) {
            case "+" -> new CheckedAdd(left, right);
            case "-" -> new CheckedSubtract(left, right);
            case "*" -> new CheckedMultiply(left, right);
            case "/" -> new CheckedDivide(left, right);
            case "set" -> new SetBit(left, right);
            case "clear" -> new ClearBit(left, right);
            case "min" -> new Min(left, right);
            case "max" -> new Max(left, right);
            default -> throw new IllegalArgumentException("Unknown operator sign '" + sign + "'");
        };
    }

    public AbstractUnaryOperator getUnaryOperator(String sign, CommonExpression operand) {
        return switch (sign) {
            case "-" -> new CheckedNegate(operand);
            case "count" -> new CountBits(operand);
            default -> throw new IllegalArgumentException("Unknown operator sign '" + sign + "'");
        };
    }
}
