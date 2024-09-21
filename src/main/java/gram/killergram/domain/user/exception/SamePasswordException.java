package gram.killergram.domain.user.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class SamePasswordException extends KillerGramException {

    public static final KillerGramException EXCEPTION =
            new SamePasswordException();

    public SamePasswordException() {
        super(ErrorCode.SAME_PASSWORD);
    }
}
