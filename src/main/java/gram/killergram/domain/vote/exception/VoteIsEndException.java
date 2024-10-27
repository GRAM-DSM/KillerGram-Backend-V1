package gram.killergram.domain.vote.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class VoteIsEndException extends KillerGramException {

    public static final KillerGramException EXCEPTION = new VoteIsEndException();

    private VoteIsEndException() {
        super(ErrorCode.VOTE_IS_END);
    }
}
