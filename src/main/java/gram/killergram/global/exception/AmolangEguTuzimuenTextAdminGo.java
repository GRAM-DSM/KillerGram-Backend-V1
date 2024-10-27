package gram.killergram.global.exception;

import gram.killergram.global.error.ErrorCode;
import gram.killergram.global.error.KillerGramException;

public class AmolangEguTuzimuenTextAdminGo extends KillerGramException {
    public static final KillerGramException EXCEPTION = new AmolangEguTuzimuenTextAdminGo();

    private AmolangEguTuzimuenTextAdminGo() {
        super(ErrorCode.THIS_IS_SERVER_PUNG);
    }
}
