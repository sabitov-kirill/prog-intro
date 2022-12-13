package expression;

public abstract class AbstractOperator implements CommonExpression {
    private final int priority;

    protected int getPriority() {
        return priority;
    }

    protected AbstractOperator(int priority) {
        this.priority = priority;
    }
}
