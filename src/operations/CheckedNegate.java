package operations;

import base.TripleExpression;
import exceptions.OverflowException;
import operator.Operator;

public class CheckedNegate<T> extends AbstractUnaryOperation<T> {
    public CheckedNegate(TripleExpression<T> x, Operator<T> operator) {
        super(x, operator);
    }

    protected T calculate(T x) throws OverflowException {
        return operator.not(x);
    }

}
