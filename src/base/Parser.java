package base;

import operator.Operator;

public interface Parser<T> {
    TripleExpression<T> parse(String expression, Operator<T> operator) throws Exception;
}