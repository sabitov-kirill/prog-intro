package expression;

public class Max extends AbstractBinaryOperator {
    public Max(CommonExpression left, CommonExpression right) {
        super(left, right, false, false, false, 50);
    }

    @Override
    public String getOperatorSign() {
        return "max";
    }

    @Override
    public int evaluateImpl(int a, int b) {
        return Math.max(a, b);
    }

    @Override
    public double evaluateImpl(double a, double b) {
        return a >= b ? a : b;
    }
}
