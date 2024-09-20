package gram.killergram.domain.user.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class UserAlreadyExistsException extends KillerGramException {

    public static final KillerGramException EXCEPTION =
            new UserAlreadyExistsException();

    private UserAlreadyExistsException() {
        super(ErrorCode.USER_ALREADY_EXISTS);
    }
}
