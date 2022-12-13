
package expression;

public abstract class AbstractBinaryOperator extends AbstractOperator {
    private final CommonExpression left, right;
    private final boolean associative;
    private final boolean distributivity;
    private final boolean distributive;

    public AbstractBinaryOperator(CommonExpression left, CommonExpression right,
                                  boolean associative, boolean distributivity, boolean distributive,
                                  int priority) {
        super(priority);
        
        this.left = left;
        this.right = right;
        this.associative = associative;
        this.distributivity = distributivity;
        this.distributive = distributive;
    }

    public abstract String getOperatorSign();

    public abstract int evaluateImpl(int a, int b);

    public abstract double evaluateImpl(double a, double b);

    @Override
    public int evaluate(int x) {
        return evaluateImpl(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return evaluateImpl(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluateImpl(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        // So slow btw...
        // return String.format("(%s %s %s)", left, getOperatorSign(), right);
        return "(" + left + " " + getOperatorSign() + " " + right + ")";
    }

    @Override
    public String toMiniString() {
        boolean leftBrackets = left instanceof AbstractBinaryOperator op && getPriority() > op.getPriority();
        boolean rightBrackets = right instanceof AbstractBinaryOperator op
                && (getPriority() > op.getPriority() || (!associative && getPriority() == op.getPriority())
                || (this.distributivity && !op.distributive));

        String leftString = leftBrackets ? "(" + left.toMiniString() + ")" : left.toMiniString();
        String rightString = rightBrackets ? "(" + right.toMiniString() + ")" : right.toMiniString();

        return leftString + " " + getOperatorSign() + " " + rightString;
    }

    @Override
    public int hashCode() {
        return (left.hashCode() * 47 - right.hashCode() * 29) * getOperatorSign().hashCode() * 3;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass() == this.getClass()) {
            AbstractBinaryOperator otherBinaryOperation = (AbstractBinaryOperator) other;
            return left.equals(otherBinaryOperation.left) && right.equals(otherBinaryOperation.right);
        }
        return false;
    }
}
