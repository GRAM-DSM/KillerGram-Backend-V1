package gram.killergram.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(401, "Invalid Token"),
    EXPIRED_TOKEN(401, "Expired Token"),
    PASSWORD_MISMATCH(401, "Password Mismatch"),

    USER_NOT_FOUND(404, "User Not Found"),
    USER_ALREADY_EXITS(409, "User Already Exits");

    private final int status;
    private final String message;
}
