package operator;

import exceptions.IllegalConstantException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;

public class IntegerOperator implements Operator<Integer> {
    public boolean checked;

    public IntegerOperator(boolean b) {
        this.checked = b;
    }

    @Override
    public Integer parseNumber(String number) throws NumberFormatException, IllegalConstantException {
        try {
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e) {
            throw new IllegalConstantException("-" + number);
        }
    }

    @Override
    public Integer add(Integer x, Integer y) throws OverflowException {
        if (checked) {
            checkAdd(x, y);
        }
        return x + y;
    }

    private void checkAdd(Integer x, Integer y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException("Add");
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException("Add");
        }
    }


    @Override
    public Integer sub(Integer x, Integer y) throws OverflowException {
        if (checked) {
            checkSub(x, y);
        }
        return x - y;
    }

    private void checkSub(Integer x, Integer y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new OverflowException("Subtract");
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException("Subtract");
        }
    }

    @Override
    public Integer mul(Integer x, Integer y) throws OverflowException {
        if (checked) {
            checkMul(x, y);
        }
        return x * y;
    }

    private void checkMul(Integer x, Integer y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new OverflowException("Multiply");
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new OverflowException("Multiply");
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new OverflowException("Multiply");
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException("Multiply");
        }
    }


    @Override
    public Integer max(Integer x, Integer y) {
        return x > y ? x : y;
    }

    @Override
    public Integer min(Integer x, Integer y) {
        return x > y ? y : x;
    }

    @Override
    public Integer mod(Integer x, Integer y) throws IllegalOperationException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        return x % y;
    }

    @Override
    public Integer div(Integer x, Integer y) throws IllegalOperationException, OverflowException {
        if (checked) {
            checkDiv(x, y);
        }
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        return x / y;
    }

    public void checkDiv(Integer x, Integer y) throws OverflowException, IllegalOperationException {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException("Divide");
        }
    }


    @Override
    public Integer not(Integer x) throws OverflowException {
        if (checked) {
            checkNot(x);
        }
        return -x;
    }

    public void checkNot(Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Negate");
        }
    }

    @Override
    public Integer abs(Integer x) throws OverflowException {
        if (checked) {
            checkAbs(x);
        }
        return max(x, -x);
    }

    public void checkAbs(Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Abs");
        }
    }

    @Override
    public Integer sqr(Integer x) throws IllegalOperationException, OverflowException {
        return mul(x, x);
    }
}
