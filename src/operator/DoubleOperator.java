package operator;

import exceptions.EvaluatingException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;

public class DoubleOperator implements Operator<Double> {
    @Override
    public Double parseNumber(String number) {
        return Double.parseDouble(number);
    }

    @Override
    public Double add(Double x, Double y) throws OverflowException {
        return x + y;
    }

    @Override
    public Double sub(Double x, Double y) throws OverflowException {
        return x - y;
    }

    @Override
    public Double mul(Double x, Double y) throws OverflowException {
        return x * y;
    }

    @Override
    public Double div(Double x, Double y) throws EvaluatingException {
        if ((x / y) == 0.0 && (x < 0 || y < 0)) {
            return -0.0;
        }
        else {
            return x / y;
        }
    }


    @Override
    public Double max(Double x, Double y) {
        return x > y ? x : y;
    }

    @Override
    public Double min(Double x, Double y) {
        return x > y ? y : x;
    }

    @Override
    public Double mod(Double x, Double y) {
        return null;
    }

    @Override
    public Double not(Double x) throws OverflowException {
        return -x;
    }

    @Override
    public Double abs(Double x) throws OverflowException {
        return max(x, -x);
    }

    @Override
    public Double sqr(Double x) throws OverflowException {
        return x * x;
    }
}
