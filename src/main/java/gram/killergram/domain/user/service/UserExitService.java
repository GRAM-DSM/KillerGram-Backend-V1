package gram.killergram.domain.user.service;

import gram.killergram.domain.user.domain.User;
import gram.killergram.domain.user.exception.UserNotFoundException;
import gram.killergram.domain.user.repository.UserJpaRepository;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserExitService {
    private final UserJpaRepository userJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void execute(String token) {
        String userId = jwtTokenProvider.getAuthentication(token).getName();

        User user = userJpaRepository.findByAccountId(userId)
                        .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        userJpaRepository.delete(user);
    }
}
