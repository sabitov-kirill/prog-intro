package expression;

import expression.parser.ExpressionParser;
import expression.parser.TripleParser;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // x * x − 2 * x + 1
//        Expression testExpression = new Add(
//                new Subtract(
//                        new Multiply(
//                                new Variable("x"),
//                                new Variable("x")
//                        ),
//                        new Const(2)
//                ),
//                new Const(1)
//        );
//
//        int value = Integer.parseInt(args[0]);
//        System.out.println(testExpression.evaluate(value));

        ExpressionParser parser = new ExpressionParser();
        TripleExpression ex = parser.parse("-(y)");
        System.out.println(ex.evaluate(-810592735, 23110107, 1208916683));
    }
}
