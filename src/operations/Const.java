package operations;

import base.TripleExpression;
import exceptions.IllegalConstantException;
import operator.Operator;

public class Const<T> implements TripleExpression<T> {
    private String value;
    public Operator<T> operator;

    public Const(String value, Operator<T> operator) {
        this.value = value;
        this.operator = operator;
    }

    public T evaluate(T x, T y, T z) throws IllegalConstantException {
        return operator.parseNumber(value);
    }
}
