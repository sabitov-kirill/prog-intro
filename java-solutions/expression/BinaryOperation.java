
package expression;

public abstract class BinaryOperation implements CommonExpression {
    private final CommonExpression left, right;
    private final boolean associative;
    private final boolean distributtivity;
    private final boolean distributive;
    private final int priority;

    public BinaryOperation(CommonExpression left, CommonExpression right, boolean associative, boolean distributtivity, boolean distributive, int priority) {
        this.left = left;
        this.right = right;
        this.associative = associative;
        this.distributtivity = distributtivity;
        this.distributive = distributive;
        this.priority = priority;
    }

    public abstract String getOperatorSign();

    public abstract int evaluateValues(int a, int b);

    public abstract double evaluateValues(double a, double b);

    @Override
    public int evaluate(int x) {
        return evaluateValues(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return evaluateValues(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluateValues(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        // So slow btw...
        // return String.format("(%s %s %s)", left, getOperatorSign(), right);
        return "(" + left + " " + getOperatorSign() + " " + right + ")";
    }

    @Override
    public String toMiniString() {
        boolean leftBrackets = left instanceof BinaryOperation op && priority > op.priority;
        boolean rightBrackets = right instanceof BinaryOperation op
                && (priority > op.priority || (!associative && priority == op.priority)
                || (this.distributtivity && !op.distributive));

        String leftString = leftBrackets ? "(" + left.toMiniString() + ")" : left.toMiniString();
        String rightString = rightBrackets ? "(" + right.toMiniString() + ")" : right.toMiniString();

        return leftString + " " + getOperatorSign() + " " + rightString;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass() == this.getClass()) {
            BinaryOperation otherBinaryOperation = (BinaryOperation) other;
            return otherBinaryOperation.left.equals(left) && otherBinaryOperation.right.equals(right);
        }
        return false;
    }
}
