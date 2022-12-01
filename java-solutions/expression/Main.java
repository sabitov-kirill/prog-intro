package expression;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // x * x − 2 * x + 1
        Expression testExpression = new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Const(2)
                ),
                new Const(1)
        );

        int value = Integer.parseInt(args[0]);
        System.out.println(testExpression.evaluate(value));
    }
}
