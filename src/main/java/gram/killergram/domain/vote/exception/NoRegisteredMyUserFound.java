package gram.killergram.domain.vote.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

// If me not exist vote, then throw this error
public class NoRegisteredMyUserFound extends KillerGramException {

    public static final KillerGramException EXCEPTION = new NoRegisteredMyUserFound();

    public NoRegisteredMyUserFound() {
        super(ErrorCode.NO_REGISTERED_MY_USER_FOUND);
    }
}
