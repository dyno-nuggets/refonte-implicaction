package com.dynonuggets.refonteimplicaction.auth.service;

import com.dynonuggets.refonteimplicaction.auth.domain.model.RefreshToken;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.RefreshTokenRepository;
import com.dynonuggets.refonteimplicaction.auth.error.AuthenticationException;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.RefreshTokenDto;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.REFRESH_TOKEN_EXPIRED;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenDto generateRefreshToken() {
        final RefreshToken refreshToken = RefreshToken.builder()
                .token(randomUUID().toString())
                .creationDate(now())
                .build();
        return toTokenDto(refreshTokenRepository.save(refreshToken));
    }

    public void validateRefreshToken(final String token) throws ImplicactionException {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new AuthenticationException(REFRESH_TOKEN_EXPIRED));
    }

    public void deleteRefreshToken(final String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    private RefreshTokenDto toTokenDto(final RefreshToken model) {
        return RefreshTokenDto.builder()
                .token(model.getToken())
                .creationDate(model.getCreationDate())
                .build();
    }
}
