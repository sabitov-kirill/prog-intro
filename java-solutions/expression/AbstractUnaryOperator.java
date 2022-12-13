
package expression;

public abstract class AbstractUnaryOperator extends AbstractOperator {
    private final CommonExpression operand;

    public AbstractUnaryOperator(CommonExpression operand, int priority) {
        super(priority);

        this.operand = operand;
    }

    public abstract String getOperatorSign();
    public abstract int evaluateImpl(int a);
    public abstract double evaluateImpl(double a);

    @Override
    public int evaluate(int x) {
        return evaluateImpl(operand.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return evaluateImpl(operand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluateImpl(operand.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return getOperatorSign() + "(" + operand + ")";
    }

    @Override
    public String toMiniString() {
        boolean brackets = operand instanceof AbstractOperator op && getPriority() > op.getPriority();
        return getOperatorSign() + (brackets ? "(" : " ") + operand.toMiniString() + (brackets ? ")" : "");
    }

    @Override
    public int hashCode() {
        return operand.hashCode() * 83 * getOperatorSign().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass() == this.getClass()) {
            AbstractUnaryOperator otherBinaryOperation = (AbstractUnaryOperator) other;
            return operand.equals(otherBinaryOperation.operand);
        }
        return false;
    }
}
