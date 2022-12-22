package expression;

import expression.parser.ExpressionParser;
import expression.parser.TripleParser;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
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
