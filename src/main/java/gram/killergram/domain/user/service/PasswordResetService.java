package gram.killergram.domain.user.service;

import gram.killergram.domain.email.domain.Email;
import gram.killergram.domain.email.exception.InvalidVerificationCodeException;
import gram.killergram.domain.email.exception.UnauthorizedRequestException;
import gram.killergram.domain.email.repository.EmailCrudRepository;
import gram.killergram.domain.user.domain.User;
import gram.killergram.domain.user.exception.SamePasswordException;
import gram.killergram.domain.user.exception.UserNotFoundException;
import gram.killergram.domain.user.presentation.dto.request.PasswordResetRequest;
import gram.killergram.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailCrudRepository emailCrudRepository;

    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        User user = userJpaRepository.findByAccountId(passwordResetRequest.getAccountId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Email email = emailCrudRepository.findById(passwordResetRequest.getAccountId())
                .orElseThrow(() -> InvalidVerificationCodeException.EXCEPTION);
        if (email.getAuthorizationStatus()) {
            if (passwordEncoder.matches(passwordResetRequest.getPassword(), user.getPassword())) {
                throw SamePasswordException.EXCEPTION;
            }

            user.updatePassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
            userJpaRepository.save(user);

            emailCrudRepository.delete(email);
        } else {
            throw UnauthorizedRequestException.EXCEPTION;
        }
    }
}
