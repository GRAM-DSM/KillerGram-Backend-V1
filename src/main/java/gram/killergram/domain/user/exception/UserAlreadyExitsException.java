package gram.killergram.domain.user.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class UserAlreadyExitsException extends KillerGramException {

    public static final KillerGramException EXCEPTION =
            new UserAlreadyExitsException();

    private UserAlreadyExitsException() {
        super(ErrorCode.USER_ALREADY_EXITS);
    }
}
