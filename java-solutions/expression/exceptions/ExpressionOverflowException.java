package expression.exceptions;

import expression.CommonExpression;

public class ExpressionOverflowException extends ExpressionEvaluateException {
    ExpressionOverflowException(CommonExpression expression) {
        super("Overflow", expression);
    }
}
