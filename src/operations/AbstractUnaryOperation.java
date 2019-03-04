package operations;

import base.TripleExpression;
import exceptions.EvaluatingException;
import operator.Operator;

public abstract class AbstractUnaryOperation<T> implements TripleExpression<T> {
    private TripleExpression<T> expression;
    public Operator<T> operator;
    public AbstractUnaryOperation(TripleExpression expression, Operator<T> operator) {
        this.expression = expression;
        this.operator = operator;
    }

    protected abstract T calculate(T x) throws EvaluatingException;

    public T evaluate(T x, T y, T z) throws EvaluatingException {
        T evaluated = expression.evaluate(x, y, z);
        return calculate(evaluated);
    }
}
