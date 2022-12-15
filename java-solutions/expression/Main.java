package expression;

import expression.parser.ExpressionParser;
import expression.parser.TripleParser;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // x * x − 2 * x + 1
        // Expression testExpression = new Add(
        //         new Subtract(
        //                 new Multiply(
        //                         new Variable("x"),
        //                         new Variable("x")
        //                 ),
        //                 new Const(2)
        //         ),
        //         new Const(1)
        // );
        // int value = Integer.parseInt(args[0]);
        // System.out.println(testExpression.evaluate(value));

        ExpressionParser parser = new ExpressionParser();
        TripleExpression ex = parser.parse("-y + (20 max 30) / (5 min 2)");
        // TripleExpression ex = parser.parse("20 min 30");
        System.out.println(ex.evaluate(100, 200, 300));
    }
}
