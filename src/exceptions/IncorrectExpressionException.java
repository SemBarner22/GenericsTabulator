package exceptions;

public class IncorrectExpressionException extends ParsingException {
    public IncorrectExpressionException(String s) {
        super("Incorrect expression on posititon " + s);
    }
}
