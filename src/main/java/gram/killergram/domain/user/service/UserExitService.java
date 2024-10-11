package gram.killergram.domain.user.service;

import gram.killergram.domain.user.domain.User;
import gram.killergram.domain.user.exception.UserAlreadyExistsException;
import gram.killergram.domain.user.exception.UserNotFoundException;
import gram.killergram.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
public class UserExitService {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void execute(String userId) {
        User user = userJpaRepository.findByAccountId(userId)
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        userJpaRepository.delete(user);
    }
}
