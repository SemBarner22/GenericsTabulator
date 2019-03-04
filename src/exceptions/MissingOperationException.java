package exceptions;

public class MissingOperationException extends ParsingException {
    public MissingOperationException(int index) {
        super("Missing operator " + index);
    }
}