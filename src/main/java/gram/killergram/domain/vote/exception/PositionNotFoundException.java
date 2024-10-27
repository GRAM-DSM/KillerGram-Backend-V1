package gram.killergram.domain.vote.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class PositionNotFoundException extends KillerGramException {
    public static final KillerGramException EXCEPTION = new PositionNotFoundException();

    private PositionNotFoundException() {
        super(ErrorCode.POSITION_NOT_FOUND);
    }
}
