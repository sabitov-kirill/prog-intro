package expression;

public class Subtract extends BinaryOperation {
    public Subtract(CommonExpression left, CommonExpression right) {
        super(left, right, false, 1);
    }

    @Override
    public String getOperatorSign() {
        return "-";
    }

    @Override
    public int evaluateValues(int a, int b) {
        return a - b;
    }

    @Override
    public double evaluateValues(double a, double b) {
        return a - b;
    }
}
