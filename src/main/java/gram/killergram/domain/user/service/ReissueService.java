package gram.killergram.domain.user.service;

import gram.killergram.domain.user.domain.RefreshToken;
import gram.killergram.domain.user.presentation.dto.request.RefreshTokenRequest;
import gram.killergram.domain.user.presentation.dto.response.TokenResponse;
import gram.killergram.domain.user.repository.RefreshTokenJpaRepository;
import gram.killergram.global.exception.RefreshTokenException;
import gram.killergram.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Transactional
    public TokenResponse reissue(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenJpaRepository.findById(request.getRefreshToken())
                .orElseThrow(() -> RefreshTokenException.EXCEPTION);

        return jwtTokenProvider.receiveToken(refreshToken.getAccountId());
    }
}
