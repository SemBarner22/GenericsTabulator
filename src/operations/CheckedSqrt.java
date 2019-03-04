package operations;

import base.TripleExpression;
import exceptions.EvaluatingException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;
import operator.Operator;

public class CheckedSqrt<T> extends AbstractUnaryOperation<T> {
    public CheckedSqrt(TripleExpression<T> x, Operator<T> operator) {
        super(x, operator);
    }

    protected T calculate(T x) throws IllegalOperationException, OverflowException {
        return operator.sqr(x);
    }
}
