package base;

import exceptions.EvaluatingException;

public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws EvaluatingException;
}