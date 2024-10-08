package gram.killergram.domain.email.presentation;

import gram.killergram.domain.email.presentation.dto.request.EmailValidCodeRequest;
import gram.killergram.domain.email.presentation.dto.request.EmailVerificationRequest;
import gram.killergram.domain.email.service.EmailSenderService;
import gram.killergram.domain.email.service.EmailVerificationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class EmailController {

    private final EmailSenderService emailSenderService;
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/send-verification")
    public void emailSend(@Valid @RequestBody EmailVerificationRequest emailVerificationRequest) throws MessagingException, IOException {
        emailSenderService.sendVerificationEmail(emailVerificationRequest);
    }

    @PostMapping("/verify-email")
    public void emailVerify(@Valid @RequestBody EmailValidCodeRequest emailValidCodeRequest) throws MessagingException, IOException {
        emailVerificationService.verifyEmail(emailValidCodeRequest);
    }
}
