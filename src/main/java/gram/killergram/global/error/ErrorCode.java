package gram.killergram.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_CODE_MISMATCH(401, "Email code mismatch"),
    EXPIRED_TOKEN(401, "Expired Token"),
    PASSWORD_MISMATCH(401, "Password Mismatch"),

    USER_NOT_FOUND(404, "User Not Found"),
    USER_ALREADY_EXISTS(409, "User Already Exists"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int status;
    private final String message;
}
