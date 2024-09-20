package gram.killergram.domain.user.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class PasswordMismatchException extends KillerGramException {

    public static final KillerGramException EXCEPTION =
            new PasswordMismatchException();

    private PasswordMismatchException() {
        super(ErrorCode.PASSWORD_MISMATCH);
    }
}
