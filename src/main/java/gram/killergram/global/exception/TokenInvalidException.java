package gram.killergram.global.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class TokenInvalidException extends KillerGramException {

    public static final KillerGramException EXCEPTION = new TokenInvalidException();

    private TokenInvalidException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
