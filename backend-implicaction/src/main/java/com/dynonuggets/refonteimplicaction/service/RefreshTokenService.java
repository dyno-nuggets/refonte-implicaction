package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.RefreshTokenDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.model.RefreshToken;
import com.dynonuggets.refonteimplicaction.repository.RefreshTokenRepository;
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

    public void validateRefreshToken(String token) throws ImplicactionException {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ImplicactionException("Invalid refresh token"));
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
