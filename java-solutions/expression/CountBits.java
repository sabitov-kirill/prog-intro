package expression;

public class CountBits extends AbstractUnaryOperator {
    public CountBits(CommonExpression operand) {
        super(operand, 300);
    }

    @Override
    public String getOperatorSign() {
        return "count";
    }

    @Override
    public int evaluateImpl(int a) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            count += (a >> i) & 1;
        }

        return count;
    }

    @Override
    public double evaluateImpl(double a) {
        return evaluateImpl((int) a);
    }
}
