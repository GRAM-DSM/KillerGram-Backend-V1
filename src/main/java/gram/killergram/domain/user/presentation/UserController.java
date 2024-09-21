package gram.killergram.domain.user.presentation;

import gram.killergram.domain.user.exception.email.EmailVerificationFailedException;
import gram.killergram.domain.user.exception.email.FailedToSendEmailException;
import gram.killergram.domain.user.presentation.dto.request.EmailValidCodeRequest;
import gram.killergram.domain.user.presentation.dto.request.StudentSignUpRequest;
import gram.killergram.domain.user.presentation.dto.request.UserLoginRequest;
import gram.killergram.domain.user.presentation.dto.request.EmailVerificationRequest;
import gram.killergram.domain.user.domain.service.StudentSignUpService;
import gram.killergram.domain.user.domain.service.UserLoginService;
import gram.killergram.domain.user.domain.service.EmailSenderService;
import gram.killergram.domain.user.domain.service.EmailVerificationService;
import gram.killergram.domain.user.presentation.dto.response.TokenResponse;
import gram.killergram.domain.user.presentation.dto.response.VerifiedEmailResponse;
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

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public TokenResponse userLogin(@RequestBody @Valid UserLoginRequest request) {
        return userLoginService.execute(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/send-verification")
    public void sendVerificationEmail(@RequestBody @Valid EmailVerificationRequest request) {
        try {
            String verificationCode = emailSenderService.sendVerificationEmail(request.getEmail());
            emailVerificationService.saveVerificationCode(request.getEmail(), verificationCode);
        } catch (MessagingException | IOException e) {
            throw FailedToSendEmailException.EXCEPTION;
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/verify-email")
    public VerifiedEmailResponse verifyEmail(@RequestBody @Valid EmailValidCodeRequest request) {
        boolean isVerified = emailVerificationService.verifyEmail(request.getEmail(), request.getCode());
        if (isVerified) {
            return new VerifiedEmailResponse(request.getEmail());
        } else {
            throw EmailVerificationFailedException.EXCEPTION;
        }
    }
}