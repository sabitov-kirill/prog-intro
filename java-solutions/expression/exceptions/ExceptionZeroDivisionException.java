package expression.exceptions;

import expression.CommonExpression;

public class ExceptionZeroDivisionException extends ExpressionEvaluateException {
    ExceptionZeroDivisionException(CommonExpression expression) {
        super("Zero Division", expression);
    }
}
