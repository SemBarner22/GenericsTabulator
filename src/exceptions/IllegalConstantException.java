package exceptions;

public class IllegalConstantException extends EvaluatingException {
    public IllegalConstantException(String reason) {
        super("Constant '" + reason);
    }
}