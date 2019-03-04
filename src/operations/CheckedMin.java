package operations;

import base.TripleExpression;
import operator.Operator;

public class CheckedMin<T> extends AbstractBinaryOperation<T> {
    public CheckedMin(TripleExpression<T> firstExpression, TripleExpression<T> secondExpression, Operator<T> operator) {
        super(firstExpression, secondExpression, operator);
    }

    protected T calculate(T x, T y) {
        return operation.min(x, y);
    }

}
