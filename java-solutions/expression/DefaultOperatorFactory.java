package expression;

public class DefaultOperatorFactory implements OperatorFactory {
    public AbstractBinaryOperator getBinaryOperator(String sign, CommonExpression left, CommonExpression right) {
        return switch (sign) {
            case "+" -> new Add(left, right);
            case "-" -> new Subtract(left, right);
            case "set" -> new SetBit(left, right);
            case "clear" -> new ClearBit(left, right);
            case "*" -> new Multiply(left, right);
            case "/" -> new Divide(left, right);
            case "min" -> new Min(left, right);
            case "max" -> new Max(left, right);
            default -> throw new IllegalArgumentException("Unknown operator sign '" + sign + "'");
        };
    }

    public AbstractUnaryOperator getUnaryOperator(String sign, CommonExpression operand) {
        return switch (sign) {
            case "-" -> new Negate(operand);
            case "count" -> new CountBits(operand);
            default -> throw new IllegalArgumentException("Unknown operator sign '" + sign + "'");
        };
    }
}
