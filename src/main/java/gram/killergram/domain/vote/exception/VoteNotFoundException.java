package gram.killergram.domain.vote.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class VoteNotFoundException extends KillerGramException {

    public static final KillerGramException EXCEPTION = new VoteNotFoundException();

    private VoteNotFoundException() {
        super(ErrorCode.VOTE_NOT_FOUND);
    }
}
