package expression;

public class Add extends BinaryOperation {
    public Add(CommonExpression left, CommonExpression right) {
        super(left, right, true, 1);
    }

    @Override
    public String getOperatorSign() {
        return "+";
    }

    public int evaluateValues(int a, int b) {
        return a + b;
    }

    @Override
    public double evaluateValues(double a, double b) {
        return a + b;
    }
}
