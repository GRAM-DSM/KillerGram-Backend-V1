package gram.killergram.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KillerGramException extends RuntimeException {

    private final ErrorCode errorCode;

}
