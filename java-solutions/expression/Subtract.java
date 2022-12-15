package expression;

public class Subtract extends AbstractBinaryOperator {
    public Subtract(CommonExpression left, CommonExpression right) {
        super(left, right, false, false, true, 100);
    }

    @Override
    public String getOperatorSign() {
        return "-";
    }

    @Override
    public int evaluateImpl(int a, int b) {
        return a - b;
    }

    @Override
    public double evaluateImpl(double a, double b) {
        return a - b;
    }
}
