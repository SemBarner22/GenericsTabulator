package exceptions;

public class ExtraCloseBracketException extends ParsingException {
    public ExtraCloseBracketException(int index) {
        super("Extra closing bracket on position " + index);
    }
}