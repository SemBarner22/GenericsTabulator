package operations;

import base.TripleExpression;
import exceptions.EvaluatingException;
import exceptions.OverflowException;
import operator.Operator;

public class CheckedSubtract<T> extends AbstractBinaryOperation<T> {
    public CheckedSubtract(TripleExpression firstExpression, TripleExpression secondExpression, Operator<T> operator) {
        super(firstExpression, secondExpression, operator);
    }

    protected T calculate(T x, T y) throws OverflowException {
        return operation.sub(x, y);
    }
}
