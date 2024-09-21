package gram.killergram.domain.user.presentation;

import gram.killergram.domain.user.exception.FailedToSendEmailException;
import gram.killergram.domain.user.exception.UserAlreadyExistsException;
import gram.killergram.domain.user.presentation.dto.request.EmailValidCodeRequest;
import gram.killergram.domain.user.presentation.dto.request.StudentSignUpRequest;
import gram.killergram.domain.user.presentation.dto.request.UserLoginRequest;
import gram.killergram.domain.user.presentation.dto.request.EmailVerificationRequest;
import gram.killergram.domain.user.domain.service.StudentSignUpService;
import gram.killergram.domain.user.domain.service.UserLoginService;
import gram.killergram.domain.user.domain.service.EmailSenderService;
import gram.killergram.domain.user.domain.service.EmailVerificationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final StudentSignUpService studentSignUpService;
    private final UserLoginService userLoginService;
    private final EmailSenderService emailSenderService;
    private final EmailVerificationService emailVerificationService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/student")
    public void studentSignUp(@RequestBody @Valid StudentSignUpRequest request) {
        studentSignUpService.execute(request);
    }

    @PostMapping("/login")
    public void userLogin(@RequestBody @Valid UserLoginRequest request) {
        userLoginService.execute(request);
    }

    @PostMapping("/send-verification")
    public ResponseEntity<Map<String, String>> sendVerificationEmail(@RequestBody @Valid EmailVerificationRequest request) {
        try {
            String verificationCode = emailSenderService.sendVerificationEmail(request.getEmail());
            emailVerificationService.saveVerificationCode(request.getEmail(), verificationCode);
            return ResponseEntity.ok(Collections.singletonMap("user_email", request.getEmail()));
        } catch (MessagingException | IOException e) {
            throw FailedToSendEmailException.EXCEPTION;
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestBody @Valid EmailValidCodeRequest request) {
        boolean isVerified = emailVerificationService.verifyEmail(request.getEmail(), request.getCode());
        if (isVerified) {
            return ResponseEntity.ok(Collections.singletonMap("user_email", request.getEmail()));
        } else {
            throw FailedToSendEmailException.EXCEPTION;
        }
    }
}