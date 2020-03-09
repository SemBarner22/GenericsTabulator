package parsing;

import exceptions.EvaluatingException;
import exceptions.ParsingException;
import operator.FloatOperator;

public class Main extends ExpressionParser {
    public static void main(String args[]) throws ParsingException, EvaluatingException {
        new Main();
    }
    private Main() throws ParsingException, EvaluatingException {
        System.out.println(parse("1 + 5 mod 3", new FloatOperator()).evaluate(-3f, -5f, -7f));
    }
}

