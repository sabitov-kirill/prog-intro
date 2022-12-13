package expression;

public class Divide extends AbstractBinaryOperator {
    public Divide(CommonExpression left, CommonExpression right) {
        super(left, right, false, false, false, 1);
    }

    @Override
    public String getOperatorSign() {
        return "/";
    }

    @Override
    public int evaluateImpl(int a, int b) {
        return a / b;
    }

    @Override
    public double evaluateImpl(double a, double b) {
        return a / b;
    }
}
