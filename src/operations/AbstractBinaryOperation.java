package operations;

import base.TripleExpression;
import exceptions.EvaluatingException;
import operator.Operator;

public abstract class AbstractBinaryOperation<T> implements TripleExpression<T> {
    private TripleExpression<T> firstExpression, secondExpression;
    Operator<T> operation;

    AbstractBinaryOperation(TripleExpression<T> firstExpression, TripleExpression<T> secondExpression, Operator<T> op) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        operation = op;
    }

    protected abstract T calculate(T x, T y) throws EvaluatingException;

    public T evaluate(T x, T y, T z) throws EvaluatingException {
        T first = firstExpression.evaluate(x, y, z);
        T second = secondExpression.evaluate(x, y, z);
        return calculate(first, second);
    }
}
