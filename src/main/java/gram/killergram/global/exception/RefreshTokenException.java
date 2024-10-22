package gram.killergram.global.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class RefreshTokenException extends KillerGramException {

    public static final KillerGramException EXCEPTION = new RefreshTokenException();

    private RefreshTokenException() {
        super(ErrorCode.TOKEN_NOT_FOUND);
    }
}
