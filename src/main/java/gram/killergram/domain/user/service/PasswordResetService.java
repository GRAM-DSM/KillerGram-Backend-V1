package gram.killergram.domain.user.service;

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

    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        User user = userJpaRepository.findByAccountId(passwordResetRequest.getAccountId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (passwordEncoder.matches(passwordResetRequest.getPassword(), user.getPassword())) {
            throw SamePasswordException.EXCEPTION;
        }

        user.updatePassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
        userJpaRepository.save(user);
    }
}
