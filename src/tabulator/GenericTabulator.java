package tabulator;

import base.TripleExpression;
import exceptions.EvaluatingException;
import exceptions.ParsingException;
import exceptions.UnknownModeException;
import operator.*;
import parsing.ExpressionParser;

public class GenericTabulator implements Tabulator {
    Operator operator = null;

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
       /* switch (mode) {
            case "i":
                operator = new IntegerOperator();
                strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
            case "d":
                operator = new DoubleOperator();
                strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
            case "bi":
                operator = new BigIntegerOperator();
                strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
            default:
                throw new UnknownModeException("Unknown mode");
        } */
        if (mode.equals("i")) {
            operator = new IntegerOperator(true);
            return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
        } else if (mode.equals("d")) {
            operator = new DoubleOperator();
            return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
        } else if (mode.equals("bi")) {
            operator = new BigIntegerOperator();
            return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
        } else if (mode.equals("u")) {
                operator = new IntegerOperator(false);
                return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
        } else if (mode.equals("f")) {
                operator = new FloatOperator();
                return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
        } else if (mode.equals("b")) {
                operator = new ByteOperator();
                return strictTabulate(operator, expression, x1, x2, y1, y2, z1, z2);
        } else throw new UnknownModeException("Unknown mode");
    }

    public <T> Object[][][] strictTabulate(Operator<T> operator, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        TripleExpression<T> in = null;
        try {
            in = new ExpressionParser<T>().parse(expression, operator);
        } catch (ParsingException e) {
            System.out.println("Exception while parsing");
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        res[i - x1][j - y1][k - z1] = in.evaluate(operator.parseNumber(Integer.toString(i)), operator
                                .parseNumber(Integer.toString(j)), operator.parseNumber(Integer.toString(k)));
                    } catch (EvaluatingException e) {
                        res[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        //parsing.StringParser<T> = new parsing.StringParser<>();
        return res;
    }
}
