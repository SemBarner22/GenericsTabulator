package exceptions;

public class ExtraOpenBracketException extends ParsingException {
    public ExtraOpenBracketException(int index) {
        super("Extra opening bracket on position " + index);
    }
}