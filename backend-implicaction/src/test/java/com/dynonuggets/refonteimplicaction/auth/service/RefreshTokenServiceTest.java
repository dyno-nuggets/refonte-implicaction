package com.dynonuggets.refonteimplicaction.auth.service;

import com.dynonuggets.refonteimplicaction.auth.domain.model.RefreshToken;
import com.dynonuggets.refonteimplicaction.auth.domain.repository.RefreshTokenRepository;
import com.dynonuggets.refonteimplicaction.auth.dto.RefreshTokenDto;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.REFRESH_TOKEN_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertImplicactionException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @Mock
    RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    RefreshTokenService refreshTokenService;

    @Test
    void should_generate_refresh_token() {
        // given
        final RefreshToken expectedToken = RefreshToken.builder().build();
        given(refreshTokenRepository.save(any())).willReturn(expectedToken);

        // when
        final RefreshTokenDto actualToken = refreshTokenService.generateRefreshToken();

        // then
        assertThat(actualToken).usingRecursiveComparison()
                .isEqualTo(expectedToken);
        verify(refreshTokenRepository, times(1)).save(any());
    }

    @Nested
    @DisplayName("# validateRefreshToken")
    class ValidateRefreshTokenTest {
        @Test
        @DisplayName("ne doit rien faire quand le token existe")
        void should_do_nothing_when_token_exists() {
            // given
            final String token = "token";
            given(refreshTokenRepository.existsByToken(token)).willReturn(true);

            // when
            refreshTokenService.validateRefreshToken(token);

            // then
            verify(refreshTokenRepository, times(1)).existsByToken(token);
        }

        @Test
        @DisplayName("doit lancer une exception quand le token n'existe pas")
        void should_throw_exception_when_token_does_not_exists() {
            // given
            final String token = "token";
            given(refreshTokenRepository.existsByToken(token)).willReturn(false);

            // when
            final ImplicactionException actualException = assertThrows(ImplicactionException.class, () -> refreshTokenService.validateRefreshToken(token));

            // then
            assertImplicactionException(actualException, EntityNotFoundException.class, REFRESH_TOKEN_NOT_FOUND);
            verify(refreshTokenRepository, times(1)).existsByToken(token);
        }
    }

}
