package gram.killergram.domain.email.service;

import gram.killergram.domain.email.domain.Email;
import gram.killergram.domain.email.exception.InvalidVerificationCodeException;
import gram.killergram.domain.email.exception.MismatchVerificationCodeException;
import gram.killergram.domain.email.presentation.dto.request.EmailValidCodeRequest;
import gram.killergram.domain.email.repository.EmailCrudRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailCrudRepository emailCrudRepository;

    @Transactional
    public void verifyEmail(EmailValidCodeRequest emailValidCodeRequest) {
        Optional<Email> email = emailCrudRepository.findById(emailValidCodeRequest.getEmail());

        if (email.isEmpty()) {
            throw InvalidVerificationCodeException.EXCEPTION;
        }
        Email emailEntity = email.get();
        if (!emailValidCodeRequest.getCode().equals(emailEntity.getAuthorizationToken())) {
            throw MismatchVerificationCodeException.EXCEPTION;
        }
        if (emailEntity.getCertifiedTime().isBefore(LocalDateTime.now())) {
            throw InvalidVerificationCodeException.EXCEPTION;
        }
        emailEntity.changeStatusTrue();

    }
}
