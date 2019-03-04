package exceptions;

public class MissingOperandException extends ParsingException {
    public MissingOperandException(int index) {
        super("Missing operand on position " + index );
    }
}