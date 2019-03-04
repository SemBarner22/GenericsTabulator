package operations;

import base.TripleExpression;
import exceptions.EvaluatingException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;
import operator.Operator;

public class CheckedDivide<T> extends AbstractBinaryOperation<T> {
    public CheckedDivide(TripleExpression firstExpression, TripleExpression secondExpression, Operator<T> operator) {
        super(firstExpression, secondExpression, operator);
    }

    protected T calculate(T x, T y) throws EvaluatingException {
        return operation.div(x, y);
    }

}
