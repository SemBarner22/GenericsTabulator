package exceptions;

import exceptions.ParsingException;

public class UnknownIdentifierException extends ParsingException {
    public UnknownIdentifierException(int index) {
        super("Unknown identifier on position " + index);
    }
}