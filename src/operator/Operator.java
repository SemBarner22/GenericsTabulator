package operator;

import exceptions.EvaluatingException;
import exceptions.IllegalConstantException;
import exceptions.IllegalOperationException;
import exceptions.OverflowException;

public abstract interface Operator<T> {
    T parseNumber(final String number) throws IllegalConstantException;

    T add(final T x, final T y) throws OverflowException;

    T sub(final T x, final T y) throws OverflowException;

    T mul(final T x, final T y) throws OverflowException;

    T max(final T x, final T y);

    T min(final T x, final T y);

    T mod(final T x, final T y);

    T div(final T x, final T y) throws EvaluatingException;

    T not(final T x) throws OverflowException;

    T abs(final T x) throws OverflowException;

    T sqr(final T x) throws OverflowException, IllegalOperationException;
}
