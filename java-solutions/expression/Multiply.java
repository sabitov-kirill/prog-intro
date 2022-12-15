package expression;

public class Multiply extends AbstractBinaryOperator {
    public Multiply(CommonExpression left, CommonExpression right) {
        super(left, right, true, true, true, 200);
    }

    @Override
    public String getOperatorSign() {
        return "*";
    }

    @Override
    public int evaluateImpl(int a, int b) {
        return a * b;
    }

    @Override
    public double evaluateImpl(double a, double b) {
        return a * b;
    }
}
