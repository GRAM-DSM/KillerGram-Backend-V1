package gram.killergram.domain.email.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class MissMatchVerificationCodeException extends KillerGramException {

    public static final KillerGramException EXCEPTION =
            new MissMatchVerificationCodeException();

    private MissMatchVerificationCodeException() {
        super(ErrorCode.VERIFICATIONCODE_MISSMATCH);
    }
}
