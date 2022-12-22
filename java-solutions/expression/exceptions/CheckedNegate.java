package expression.exceptions;

import expression.CommonExpression;
import expression.Negate;

public class CheckedNegate extends Negate {
    public CheckedNegate(CommonExpression operand) {
        super(operand);
    }

    @Override
    public int evaluateImpl(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new ExpressionOverflowException(this);
        }

        return -a;
    }
}
