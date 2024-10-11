package gram.killergram.domain.user.service;

import gram.killergram.domain.user.domain.User;
import gram.killergram.domain.user.exception.UserAlreadyExistsException;
import gram.killergram.domain.user.exception.UserNotFoundException;
import gram.killergram.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserExitService {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void execute() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        User user = userJpaRepository.findByAccountId(userId)
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        userJpaRepository.delete(user);
    }
}
