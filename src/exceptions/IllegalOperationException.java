package exceptions;

public class IllegalOperationException extends EvaluatingException {
    public IllegalOperationException(String message) {
        super("Illegal operator: " + message);
    }
}