package expression;

public class Multiply extends BinaryOperation {
    public Multiply(CommonExpression left, CommonExpression right) {
        super(left, right, true, 2);
    }

    @Override
    public String getOperatorSign() {
        return "*";
    }

    @Override
    public int evaluateValues(int a, int b) {
        return a * b;
    }

    @Override
    public double evaluateValues(double a, double b) {
        return a * b;
    }
}
