package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.auth.domain.model.RefreshToken;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.RefreshTokenRepository;
import com.dynonuggets.refonteimplicaction.auth.rest.dto.RefreshTokenDto;
import com.dynonuggets.refonteimplicaction.exception.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenDto generateRefreshToken() {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .creationDate(Instant.now())
                .build();
        return toTokenDto(refreshTokenRepository.save(refreshToken));
    }

    public void validateRefreshToken(String token) throws UnauthorizedException {
        if (!refreshTokenRepository.findByToken(token).isPresent()) {
            throw new UnauthorizedException("Votre session a expiré, veuillez vous devez vous identifier.");
        }
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    private RefreshTokenDto toTokenDto(RefreshToken model) {
        return RefreshTokenDto.builder()
                .token(model.getToken())
                .creationDate(model.getCreationDate())
                .build();
    }
}
