package gram.killergram.domain.vote.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class InvalidPositionException extends KillerGramException {
    public static final KillerGramException EXCEPTION = new InvalidPositionException();

    private InvalidPositionException() {
        super(ErrorCode.INVALID_POSITION);
    }
}
