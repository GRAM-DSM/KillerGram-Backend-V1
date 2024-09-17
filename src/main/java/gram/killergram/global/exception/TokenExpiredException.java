package gram.killergram.global.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class TokenExpiredException extends KillerGramException {

    public static final KillerGramException EXCEPTION = new TokenExpiredException();

    private TokenExpiredException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
