package gram.killergram.domain.vote.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class PositionNotHasSportException extends KillerGramException {

    public static final KillerGramException EXCEPTION = new PositionNotHasSportException();

    private PositionNotHasSportException() {
        super(ErrorCode.POSITION_NOT_HAS_SPORT);
    }
}
