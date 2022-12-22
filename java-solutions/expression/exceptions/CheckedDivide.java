package expression.exceptions;

import expression.CommonExpression;
import expression.Divide;

public class CheckedDivide extends Divide {
    public CheckedDivide(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int evaluateImpl(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new ExpressionOverflowException(this);
        }

        if (b == 0) {
            throw new ExceptionZeroDivisionException(this);
        }

        return a / b;
    }
}
