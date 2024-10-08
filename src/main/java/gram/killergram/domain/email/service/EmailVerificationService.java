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

        if (email.isPresent()) {
            if (emailValidCodeRequest.getCode().equals(email.get().getAuthorizationToken())) {
                if (email.get().getCertifiedTime().isAfter(LocalDateTime.now())) {
                    email.get().changeStatusTrue();
                } else {
                    throw InvalidVerificationCodeException.EXCEPTION;
                }
            } else {
                throw MismatchVerificationCodeException.EXCEPTION;
            }
        } else {
            throw InvalidVerificationCodeException.EXCEPTION;
        }
    }
}
