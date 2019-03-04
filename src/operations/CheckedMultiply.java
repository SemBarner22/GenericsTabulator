package operations;

import base.TripleExpression;
import exceptions.EvaluatingException;
import exceptions.OverflowException;
import operator.Operator;

public class CheckedMultiply<T> extends AbstractBinaryOperation<T> {
    public CheckedMultiply(TripleExpression<T> firstExpression, TripleExpression<T> secondExpression,
                           Operator<T> operator) {
        super(firstExpression, secondExpression, operator);
    }

    protected T calculate(T x, T y) throws OverflowException {
        return operation.mul(x, y);
    }

}
