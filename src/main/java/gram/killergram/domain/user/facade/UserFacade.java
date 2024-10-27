package gram.killergram.domain.user.facade;

import gram.killergram.domain.user.domain.User;
import gram.killergram.domain.user.exception.UserNotFoundException;
import gram.killergram.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserFacade {

    private final UserJpaRepository userJpaRepository;

    // get User through accountId
    public User getByUser(String accountId) {
        return userJpaRepository.findByAccountId(accountId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    // get User UUID through accountId
    public UUID getUserId(String accountId) {
        return getByUser(accountId).getUserId();
    }
}
