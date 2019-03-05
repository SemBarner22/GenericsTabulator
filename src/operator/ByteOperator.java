package operator;

import exceptions.EvaluatingException;
import exceptions.IllegalOperationException;

public class ByteOperator implements Operator<Byte>  {

    @Override
    public Byte parseNumber(String number)  {
        return (byte) Integer.parseInt(number);
    }

    @Override
    public Byte add(Byte x, Byte y) {
        return (byte) (x + y);
    }

    @Override
    public Byte sub(Byte x, Byte y)  {
        return (byte) (x - y);
    }

    @Override
    public Byte mul(Byte x, Byte y)  {
        return (byte) (x * y);
    }

    @Override
    public Byte max(Byte x, Byte y) {
        return max(x, y);
    }

    @Override
    public Byte min(Byte x, Byte y) {
        return min(x, y);
    }

    @Override
    public Byte mod(Byte x, Byte y) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        return (byte) (x % y);
    }

    @Override
    public Byte div(Byte x, Byte y) throws EvaluatingException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        return (byte) (x / y);
    }

    @Override
    public Byte not(Byte x) {
        return (byte) (-x);
    }

    @Override
    public Byte abs(Byte x) {
        return (byte) Math.abs(x);
    }

    @Override
    public Byte sqr(Byte x) {
        return (byte) (x * x);
    }
}
