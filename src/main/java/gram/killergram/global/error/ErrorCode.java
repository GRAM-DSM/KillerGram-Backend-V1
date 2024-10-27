package gram.killergram.global.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EXPIRED_TOKEN(401, "Expired Token"),
    PASSWORD_MISMATCH(401, "Password Mismatch"),
    VERIFICATION_CODE_MISMATCH(401, "Verification code mismatch"),
    INVALID_TOKEN(401, "Invalid Token"),

    POSITION_NOT_HAS_SPORT(403, "Sport doesn't have position"),
    UNAUTHORIZED_REQUEST(403, "Unauthorized Request"),
    VOTE_IS_END(403, "Vote is already end"),
    NO_REGISTERED_MY_USER_FOUND(403, "No registered my user found"),

    POSITION_NOT_FOUND(404, "Position not found"),
    STUDENT_NOT_FOUND(404, "Student Not Found"),
    VOTE_NOT_FOUND(404, "Vote not found"),
    TOKEN_NOT_FOUND(404, "RefreshToken Not Found"),
    EMAIL_VERIFICATION_NOT_FOUND(404, "Email Verification Not Found"),
    USER_NOT_FOUND(404, "User Not Found"),

    ALREADY_VOTE_REGISTERED(409, "Already Vote Registered"),
    USER_ALREADY_EXISTS(409, "User Already Exists"),
    SAME_PASSWORD(409, "Same Password"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int status;
    private final String message;
}
