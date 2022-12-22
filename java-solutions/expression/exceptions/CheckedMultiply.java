package expression.exceptions;

import expression.CommonExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int evaluateImpl(int a, int b) {
        if (a == Integer.MIN_VALUE && b < 0 || b == Integer.MIN_VALUE && a < 0) {
            throw new ExpressionOverflowException(this);
        }

        int res = a * b;
        if (a != 0 && res / a != b) {
            throw new ExpressionOverflowException(this);
        }

        return res;
    }
}
