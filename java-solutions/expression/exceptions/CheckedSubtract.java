package expression.exceptions;

import expression.CommonExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int evaluateImpl(int a, int b) {
        if (b < 0 && a > Integer.MAX_VALUE + b || b > 0 && a < Integer.MIN_VALUE + b) {
            throw new ExpressionOverflowException(this);
        }

        return a - b;
    }
}
