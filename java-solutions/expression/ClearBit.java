package expression;

public class ClearBit extends AbstractBinaryOperator {
    public ClearBit(CommonExpression left, CommonExpression right) {
        super(left, right, false, false, true, 5);
    }

    @Override
    public String getOperatorSign() {
        return "clear";
    }

    @Override
    public int evaluateImpl(int a, int b) {
        return a & ~(1 << b);
    }

    @Override
    public double evaluateImpl(double a, double b) {
        return evaluateImpl((int) a, (int) b);
    }
}
