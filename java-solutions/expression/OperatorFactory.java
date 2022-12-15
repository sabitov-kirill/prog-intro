package expression;

import java.util.List;
import java.util.Set;

public class OperatorFactory {
    public static final List<Set<String>> OPERATORS_BY_PRIOR = List.of(
            Set.of("set", "clear"), // 5
            Set.of("+", "-"),       // 100
            Set.of("*", "/"),       // 200
            Set.of("-", "count")    // 300, Unary negation
    );

    private OperatorFactory() {
        // Not used
    }

    public static CommonExpression getBinaryOperator(String sign, CommonExpression left, CommonExpression right) {
        return switch (sign) {
            case "+" -> new Add(left, right);
            case "-" -> new Subtract(left, right);
            case "set" -> new SetBit(left, right);
            case "clear" -> new ClearBit(left, right);
            case "*" -> new Multiply(left, right);
            case "/" -> new Divide(left, right);
            default -> throw new IllegalArgumentException("Unknown operator sign '" + sign + "'");
        };
    }

    public static AbstractUnaryOperator getUnaryOperator(String sign, CommonExpression operand) {
        return switch (sign) {
            case "-" -> new Negate(operand);
            case "count" -> new CountBits(operand);
            default -> throw new IllegalArgumentException("Unknown operator sign '" + sign + "'");
        };
    }
}
