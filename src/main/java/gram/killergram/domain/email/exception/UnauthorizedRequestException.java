package gram.killergram.domain.email.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class UnauthorizedRequestException extends KillerGramException {
    public static final KillerGramException EXCEPTION = new UnauthorizedRequestException();

    public UnauthorizedRequestException() {
        super(ErrorCode.UNAUTHORIZED_REQUEST);
    }
}
