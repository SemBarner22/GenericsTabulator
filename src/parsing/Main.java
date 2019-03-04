package parsing;

import exceptions.EvaluatingException;
import exceptions.ParsingException;
import operator.BigIntegerOperator;
import operator.DoubleOperator;
import operator.FloatOperator;
import operator.IntegerOperator;

import java.math.BigInteger;

public class Main extends ExpressionParser {
    public static void main(String args[]) throws ParsingException, EvaluatingException {
        new Main();
    }
    protected Main() throws ParsingException, EvaluatingException {
        System.out.println(parse("1 + 5 mod 3", new FloatOperator()).evaluate(-3f, -5f, -7f));
    }
}

