package gram.killergram.domain.vote.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class PositionDuplicateException extends KillerGramException {
    public static final KillerGramException EXCEPTION = new PositionDuplicateException();

    private PositionDuplicateException() {
        super(ErrorCode.POSITION_DUPLICATE);
    }
}
