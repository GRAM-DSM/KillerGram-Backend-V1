package gram.killergram.domain.user.service;

import gram.killergram.domain.user.presentation.dto.request.UserLoginRequest;
import gram.killergram.domain.user.presentation.dto.response.TokenResponse;
import gram.killergram.domain.user.domain.User;
import gram.killergram.domain.user.exception.PasswordMismatchException;
import gram.killergram.domain.user.exception.UserNotFoundException;
import gram.killergram.domain.user.repository.UserJpaRepository;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserJpaRepository userJpaRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponse execute(UserLoginRequest request) {
        String accountId = request.getAccountId();
        User user = userJpaRepository.findByAccountId(accountId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            throw PasswordMismatchException.EXCEPTION;
        }

        String userAccessToken = jwtTokenProvider.createAccessToken(user.getAccountId());
        String userRefreshToken = jwtTokenProvider.createRefreshToken(user.getAccountId());

        return TokenResponse.builder()
                .accessToken(userAccessToken)
                .refreshToken(userRefreshToken)
                .build();
    }
}
