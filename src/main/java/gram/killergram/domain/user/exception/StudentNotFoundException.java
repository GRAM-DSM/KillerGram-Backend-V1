package gram.killergram.domain.user.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class StudentNotFoundException extends KillerGramException {

    public static final KillerGramException EXCEPTION = new StudentNotFoundException();

    private StudentNotFoundException() {
        super(ErrorCode.STUDENT_NOT_FOUND);
    }
}
