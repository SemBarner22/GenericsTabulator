package operator;

import exceptions.EvaluatingException;
import exceptions.IllegalConstantException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;

public class FloatOperator implements Operator<Float> {

    @Override
    public Float parseNumber(String number) throws IllegalConstantException {
        return Float.parseFloat(number);
    }

    @Override
    public Float add(Float x, Float y) throws OverflowException {
        return x + y;
    }

    @Override
    public Float sub(Float x, Float y) throws OverflowException {
        return x - y;
    }

    @Override
    public Float mul(Float x, Float y) throws OverflowException {
        return x * y;
    }

    @Override
    public Float max(Float x, Float y) {
        return max(x, y);
    }

    @Override
    public Float min(Float x, Float y) {
        return min(x, y);
    }

    @Override
    public Float mod(Float x, Float y) {
        return null;
    }

    @Override
    public Float div(Float x, Float y) throws EvaluatingException {
        return x / y;
    }

    @Override
    public Float not(Float x) throws OverflowException {
        return -x;
    }

    @Override
    public Float abs(Float x) throws OverflowException {
        return max(x, -x);
    }

    @Override
    public Float sqr(Float x) throws OverflowException, IllegalOperationException {
        return x * x;
    }
}
