package gram.killergram.domain.email.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class EmailVerificationFailedException extends KillerGramException {
    public static final EmailVerificationFailedException EXCEPTION = new EmailVerificationFailedException();

    private EmailVerificationFailedException() {
        super(ErrorCode.EMAIL_CODE_MISMATCH);
    }
}
