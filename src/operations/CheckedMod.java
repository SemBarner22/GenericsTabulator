package operations;

import base.TripleExpression;
import exceptions.EvaluatingException;
import operator.Operator;

public class CheckedMod<T> extends AbstractBinaryOperation<T> {

    public CheckedMod(TripleExpression<T> firstExpression, TripleExpression<T> secondExpression, Operator<T> op) {
        super(firstExpression, secondExpression, op);
    }

    @Override
    protected T calculate(T x, T y) throws EvaluatingException {
        return operation.mod(x, y);
    }

}
