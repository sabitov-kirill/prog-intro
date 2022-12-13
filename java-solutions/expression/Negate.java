package expression;

public class Negate extends AbstractUnaryOperator {
    public Negate(CommonExpression operand) {
        super(operand, 3);
    }

    @Override
    public String getOperatorSign() {
        return "-";
    }

    @Override
    public int evaluateImpl(int a) {
        return -a;
    }

    @Override
    public double evaluateImpl(double a) {
        return -a;
    }
}
