package tabulator;

import base.TripleExpression;
import exceptions.EvaluatingException;
import exceptions.ParsingException;
import exceptions.UnknownModeException;
import operator.*;
import parsing.ExpressionParser;

public class GenericTabulator implements Tabulator {

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        switch (mode) {
            case "i":
                Operator operator = new IntegerOperator(true);
                return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
            case "d":
                operator = new DoubleOperator();
                return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
            case "bi":
                operator = new BigIntegerOperator();
                return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
            case "u":
                operator = new IntegerOperator(false);
                return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
            case "f":
                operator = new FloatOperator();
                return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
            case "b":
                operator = new ByteOperator();
                return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
            default:
                throw new UnknownModeException("Unknown mode");
        }
    }

    private <T> Object[][][] strictTabulate(Operator<T> operator, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        TripleExpression<T> in = null;
        try {
            in = new ExpressionParser<T>().parse(expression, operator);
        } catch (ParsingException e) {
            System.out.println("Exception while parsing");
        }
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    try {
                        assert in != null;
                        res[x - x1][y - y1][z - z1] = in.evaluate(operator.parseNumber(Integer.toString(x)), operator
                                .parseNumber(Integer.toString(y)), operator.parseNumber(Integer.toString(z)));
                    } catch (EvaluatingException e) {
                        res[x - x1][y - y1][z - z1] = null;
                    }

                }
            }
        }
        return res;
    }
}
