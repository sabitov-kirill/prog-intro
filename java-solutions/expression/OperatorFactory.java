package expression;

public interface OperatorFactory {
    AbstractBinaryOperator getBinaryOperator(String sign, CommonExpression left, CommonExpression right);
    AbstractUnaryOperator getUnaryOperator(String sign, CommonExpression operand);
}
