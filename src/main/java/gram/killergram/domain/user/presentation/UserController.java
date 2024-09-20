package gram.killergram.domain.user.presentation;

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

import java.io.IOException;

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
    public String sendVerificationEmail(@RequestBody @Valid EmailVerificationRequest request) {
        try {
            String verificationCode = emailSenderService.sendVerificationEmail(request.getEmail());
            emailVerificationService.saveVerificationCode(request.getEmail(), verificationCode);
            return "Verification email sent successfully";
        } catch (MessagingException | IOException e) {
            return "Failed to send verification email";
        }
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@RequestBody @Valid EmailValidCodeRequest request) {
        boolean isVerified = emailVerificationService.verifyEmail(request.getEmail(), request.getCode());
        if (isVerified) {
            return "Email verified successfully";
        } else {
            return "Invalid verification code";
        }
    }
}