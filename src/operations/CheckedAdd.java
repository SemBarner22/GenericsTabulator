package operations;

import base.TripleExpression;
import exceptions.OverflowException;
import operator.Operator;

public class CheckedAdd<T> extends AbstractBinaryOperation<T> {
    public CheckedAdd(TripleExpression<T> firstExpression, TripleExpression<T> secondExpression, Operator<T> op) {
        super(firstExpression, secondExpression, op);
    }

    protected T calculate(T x, T y) throws OverflowException {
        return operation.add(x, y);
    }
}
