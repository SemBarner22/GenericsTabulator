package operations;

import base.TripleExpression;
import operator.Operator;

public class CheckedMax<T> extends AbstractBinaryOperation<T> {
    public CheckedMax(TripleExpression<T> firstExpression, TripleExpression<T> secondExpression, Operator<T> operator) {
        super(firstExpression, secondExpression, operator);
    }

    protected T calculate(T x, T y) {
        return operation.max(x, y);
    }

}
