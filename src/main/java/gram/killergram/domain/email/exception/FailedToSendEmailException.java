package gram.killergram.domain.email.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class FailedToSendEmailException extends KillerGramException {

    public static final KillerGramException EXCEPTION =
            new FailedToSendEmailException();

    private FailedToSendEmailException() {
        super(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
