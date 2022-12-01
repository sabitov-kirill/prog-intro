package expression;

public class Divide extends BinaryOperation {
    public Divide(CommonExpression left, CommonExpression right) {
        super(left, right, false, 2);
    }

    @Override
    public String getOperatorSign() {
        return "/";
    }

    @Override
    public int evaluateValues(int a, int b) {
        return a / b;
    }

    @Override
    public double evaluateValues(double a, double b) {
        return a / b;
    }
}
