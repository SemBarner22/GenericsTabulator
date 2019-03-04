package operations;

import base.TripleExpression;
import exceptions.EvaluatingException;
import exceptions.OverflowException;
import operator.Operator;

public class CheckedAbs<T> extends AbstractUnaryOperation<T> {
    public CheckedAbs(TripleExpression<T> x, Operator<T> operator) {
        super(x, operator);
    }

    protected T calculate(T x) throws OverflowException {
        return operator.abs(x);
    }

}
