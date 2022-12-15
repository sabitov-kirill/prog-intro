package expression;

public class Min extends AbstractBinaryOperator {
    public Min(CommonExpression left, CommonExpression right) {
        super(left, right, false, false, false, 50);
    }

    @Override
    public String getOperatorSign() {
        return "min";
    }

    @Override
    public int evaluateImpl(int a, int b) {
        return Math.min(a, b);
    }

    @Override
    public double evaluateImpl(double a, double b) {
        return Math.min(a, b);
    }
}
