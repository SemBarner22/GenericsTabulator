package parsing;

import base.Token;
import exceptions.*;
import operator.Operator;

public class StringParser<T> {
    private Token token;
    private String name;
    private String stringNumber;
    private int index;
    private String expression;
    private int balance;
    public Operator<T> operation;

    public StringParser(String expression, Operator<T> op) {
        operation = op;
        token = Token.BEGIN;
        index = 0;
        balance = 0;
        this.expression = expression;
    }

    public Token getToken() {
        return token;
    }

    public String getValue() {
        return stringNumber;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    protected void next() throws ParsingException, EvaluatingException {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
        if (index == expression.length()) {
            if (balance > 0) {
                throw new ExtraOpenBracketException(index);
            }
                token = Token.END;
            return;
        }
        char symbol = expression.charAt(index);
        if (symbol == '+') {
            checkForOperand(index);
            token = Token.ADD;
        } else if (symbol == '-') {
            if (token == Token.CONST || token == Token.VARIABLE || token == Token.CLOSEDBRACKET) {
                token = Token.SUBTRACT;
            } else {
                index++;
                if (index == expression.length()) {
                    throw new MissingOperandException(index);
                }
                while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
                    index++;
                }
                if (Character.isDigit(expression.charAt(index))) {
                    int beginOfNumber = index;
                    while (index < expression.length() && (Character.isDigit(expression.charAt(index))
                                                        || expression.charAt(index) == '.')) {
                        index++;
                    }
                    int endOfNumber = index;
                    stringNumber = "-";
                    stringNumber += expression.substring(beginOfNumber, endOfNumber);
                    token = Token.CONST;
                } else {
                    token = Token.UNARYMINUS;
                }
                index--;
            }
        } else if (symbol == '*') {
            checkForOperand(index);
            token = Token.MULTIPLY;
        } else if (symbol == '/') {
            checkForOperand(index);
            token = Token.DIVIDE;
        } else if (Character.isDigit(symbol)) {
            checkForOperation(index);
            int left = index;
            while (index < expression.length() && (Character.isDigit(expression.charAt(index))
                    || expression.charAt(index) == '.')) {
                index++;
            }
            int right = index;
            // TODO
            stringNumber = expression.substring(left, right);
            token = Token.CONST;
            index--;
        } else if (symbol == 'x' || symbol == 'y' || symbol == 'z') {
            name = "";
            name += symbol;
            token = Token.VARIABLE;
        } else if (symbol == '(') {
            checkForOperation(index);
            balance++;
            token = Token.OPENEDBRACKET;
        } else if (symbol == ')') {
            balance--;
            token = Token.CLOSEDBRACKET;
            if (balance < 0) {
                throw new ExtraCloseBracketException(index);
            }
        } else if (index + 3 < expression.length() && expression.substring(index, index + 3).equals("max")) {
            checkForOperand(index);
            token = Token.MAX;
            checkNextPosition(index + 3);
            index += 2;
        } else if (index + 3 < expression.length() && expression.substring(index, index + 3).equals("min")) {
            checkForOperand(index);
            token = Token.MIN;
            checkNextPosition(index + 3);
            index += 2;
        } else if (index + 3 < expression.length() && expression.substring(index, index + 3).equals("abs")) {
            token = Token.ABS;
            checkNextPosition(index + 3);
            index += 2;
        } else if (index + 6 < expression.length() && expression.substring(index, index + 6).equals("square")) {
            token = Token.SQR;
            checkNextPosition(index + 6);
            index += 5;
        } else if (index + 3 < expression.length() && expression.substring(index, index + 3).equals("mod")) {
            token = Token.MOD;
            checkNextPosition(index + 3);
            index += 2;
        } else {
            throw new UnknownIdentifierException(index);
        }
        index++;
    }

    private void checkForOperand(int index) throws MissingOperandException {
        if (token != Token.CONST && token != Token.VARIABLE && token != Token.CLOSEDBRACKET) {
            throw new MissingOperandException(index);
        }
    }

    private void checkForOperation(int index) throws MissingOperationException {
        if (token == Token.CLOSEDBRACKET || token == Token.VARIABLE || token == Token.CONST) {
            throw new MissingOperationException(index);
        }
    }

    private void checkNextPosition(int index) throws IncorrectExpressionException {
        if (expression.charAt(index) != '-' && expression.charAt(index) != ' ' && expression.charAt(index) != '(') {
            throw new IncorrectExpressionException("" + index);
        }
    }

}
