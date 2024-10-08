package gram.killergram.domain.email.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class MismatchVerificationCodeException extends KillerGramException {

    public static final KillerGramException EXCEPTION =
            new MismatchVerificationCodeException();

    private MismatchVerificationCodeException() {
        super(ErrorCode.VERIFICATION_CODE_MISMATCH);
    }
}
