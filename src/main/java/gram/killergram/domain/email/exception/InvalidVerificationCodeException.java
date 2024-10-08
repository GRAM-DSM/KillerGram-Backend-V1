package gram.killergram.domain.email.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class InvalidVerificationCodeException extends KillerGramException {
    public static final KillerGramException EXCEPTION =
            new InvalidVerificationCodeException();

    private InvalidVerificationCodeException() {
        super(ErrorCode.EMAIL_VERIFICATION_NOT_FOUND);
    }
}
