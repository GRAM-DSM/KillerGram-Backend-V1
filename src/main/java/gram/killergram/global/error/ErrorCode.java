package gram.killergram.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {


    EMAIL_CODE_MISMATCH(401, "Email code mismatch"),
    EXPIRED_TOKEN(401, "Expired Token"),
    PASSWORD_MISMATCH(401, "Password Mismatch"),
    VERIFICATIONCODE_MISSMATCH(401, "Verification code mismatch"),

    UNAUTHORIZED_REQUEST(403, "Unauthorized Request"),

    EMAIL_VERIFICATION_NOT_FOUND(404, "Email Verification Not Found"),
    USER_NOT_FOUND(404, "User Not Found"),

    USER_ALREADY_EXISTS(409, "User Already Exists"),
    SAME_PASSWORD(409, "Same Password"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int status;
    private final String message;
}
