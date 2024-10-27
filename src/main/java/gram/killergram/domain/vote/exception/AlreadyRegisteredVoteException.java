package gram.killergram.domain.vote.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class AlreadyRegisteredVoteException extends KillerGramException {

    public static final KillerGramException EXCEPTION = new AlreadyRegisteredVoteException();

    private AlreadyRegisteredVoteException() {
        super(ErrorCode.ALREADY_VOTE_REGISTERED);
    }
}
