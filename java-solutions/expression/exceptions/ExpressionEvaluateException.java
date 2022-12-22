package expression.exceptions;

import expression.CommonExpression;

public class ExpressionEvaluateException extends ArithmeticException {
    ExpressionEvaluateException(String message, CommonExpression expression) {
        super(message + " (for f=" + expression);
    }
}
