package gram.killergram.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KillerGramException extends RuntimeException {
    private final ErrorCode errorCode;
}
