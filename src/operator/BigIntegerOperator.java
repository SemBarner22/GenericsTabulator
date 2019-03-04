package operator;

import exceptions.EvaluatingException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;

import java.math.BigInteger;

public class BigIntegerOperator implements Operator<BigInteger> {

    @Override
    public BigInteger parseNumber(String number) {
        return new BigInteger(number);
    }

    @Override
    public BigInteger add(BigInteger x, BigInteger y) throws OverflowException {
        return x.add(y);
    }

    @Override
    public BigInteger sub(BigInteger x, BigInteger y) throws OverflowException {
        return x.subtract(y);
    }

    @Override
    public BigInteger mul(BigInteger x, BigInteger y) throws OverflowException {
        return x.multiply(y);
    }

    @Override
    public BigInteger max(BigInteger x, BigInteger y) {
        return x.max(y);
    }

    @Override
    public BigInteger min(BigInteger x, BigInteger y) {
       return x.min(y);
    }

    @Override
    public BigInteger mod(BigInteger x, BigInteger y) throws IllegalOperationException {
        checkDivide(y);
        checkModulus(y);
        return x.mod(y);
    }

    @Override
    public BigInteger div(BigInteger x, BigInteger y) throws EvaluatingException {
        checkDivide(y);
        return x.divide(y);
    }

    private void checkModulus(BigInteger y) throws IllegalOperationException {
        if (y.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalOperationException("Division by zero");
        }
    }
    private void checkDivide(BigInteger y) throws IllegalOperationException {
        if (y.equals(BigInteger.ZERO)) {
            throw new IllegalOperationException("Division by zero");
        }
    }

    @Override
    public BigInteger not(BigInteger x) throws OverflowException {
        return x.not();
    }

    @Override
    public BigInteger abs(BigInteger x) throws OverflowException {
        return x.abs();
    }

    @Override
    public BigInteger sqr(BigInteger x) throws OverflowException {
        return x.multiply(x);
    }
}
