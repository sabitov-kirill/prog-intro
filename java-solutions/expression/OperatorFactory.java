package expression;

public interface OperatorFactory {
    public AbstractBinaryOperator getBinaryOperator(String sign, CommonExpression left, CommonExpression right);
    public AbstractUnaryOperator getUnaryOperator(String sign, CommonExpression operand);
}
