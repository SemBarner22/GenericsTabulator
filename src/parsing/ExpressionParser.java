package parsing;

import base.Parser;
import base.Token;
import base.TripleExpression;
import exceptions.EvaluatingException;
import exceptions.ExtraOpenBracketException;
import exceptions.ParsingException;
import exceptions.UnknownIdentifierException;
import operations.*;
import operator.Operator;

public class ExpressionParser<T> implements Parser<T> {
    private StringParser<T> stringParser;
    private Operator<T> operation;

    public TripleExpression<T> parse(String s, Operator<T> op) throws ParsingException, EvaluatingException {
        stringParser = new StringParser<>(s, op);
        operation = op;
        return maxOrMinOperations();
    }

    private TripleExpression<T> maxOrMinOperations() throws ParsingException, EvaluatingException {
        TripleExpression<T> current = addOrSubtractOperations();
        while (true) {
            if (stringParser.getToken() == Token.MAX) {
                current = new CheckedMax<>(current, addOrSubtractOperations(), operation);
            } else if (stringParser.getToken() == Token.MIN) {
                current = new CheckedMin<>(current, addOrSubtractOperations(), operation);
            } else {
                return current;
            }
        }
    }

    private TripleExpression<T> addOrSubtractOperations() throws ParsingException, EvaluatingException {
        TripleExpression<T> current = multiplyOrDivideOrModOperations();
        while (true) {
            if (stringParser.getToken() == Token.ADD) {
                current = new CheckedAdd<>(current, multiplyOrDivideOrModOperations(), operation);
            } else if (stringParser.getToken() == Token.SUBTRACT) {
                current = new CheckedSubtract<>(current, multiplyOrDivideOrModOperations(), operation);
            } else {
                return current;
            }
        }
    }

    private TripleExpression<T> multiplyOrDivideOrModOperations() throws ParsingException, EvaluatingException {
        TripleExpression<T> current = otherOperations();
        while (true) {
            if (stringParser.getToken() == Token.MULTIPLY) {
                current = new CheckedMultiply<>(current, otherOperations(), operation);
            } else if (stringParser.getToken() == Token.DIVIDE) {
                current = new CheckedDivide<>(current, otherOperations(), operation);
            } else if (stringParser.getToken() == Token.MOD) {
                current = new CheckedMod<>(current, otherOperations(), operation);
            } else {
                return current;
            }
        }
    }

    private TripleExpression<T> otherOperations() throws ParsingException, EvaluatingException {
        stringParser.next();
        TripleExpression<T> current;
        if (stringParser.getToken() == Token.CONST) {
            current = new Const<>(stringParser.getValue(), operation);
            stringParser.next();
        } else if (stringParser.getToken() == Token.VARIABLE) {
            current = new Variable<>(stringParser.getName());
            stringParser.next();
        } else if (stringParser.getToken() == Token.OPENEDBRACKET) {
            current = maxOrMinOperations();
            if (stringParser.getToken() != Token.CLOSEDBRACKET) {
                throw new ExtraOpenBracketException(stringParser.getIndex());
            }
            stringParser.next();
        } else if (stringParser.getToken() == Token.UNARYMINUS) {
            current = new CheckedNegate<>(otherOperations(),operation);
        } else if (stringParser.getToken() == Token.ABS) {
            current = new CheckedAbs<>(otherOperations(), operation);
        } else if (stringParser.getToken() == Token.SQR) {
            current = new CheckedSqr<>(otherOperations(), operation);
        } else {
            throw new UnknownIdentifierException(stringParser.getIndex());
        }
        return current;
    }

}
