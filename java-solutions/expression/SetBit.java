package expression;

public class SetBit extends AbstractBinaryOperator {
    public SetBit(CommonExpression left, CommonExpression right) {
        super(left, right, false, false, true, 50);
    }

    @Override
    public String getOperatorSign() {
        return "set";
    }

    @Override
    public int evaluateImpl(int a, int b) {
        return a | (1 << b);
    }

    @Override
    public double evaluateImpl(double a, double b) {
        return evaluateImpl((int) a, (int) b);
    }
}
