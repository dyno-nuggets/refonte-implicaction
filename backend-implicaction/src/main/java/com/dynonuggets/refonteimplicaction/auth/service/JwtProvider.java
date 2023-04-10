package com.dynonuggets.refonteimplicaction.auth.service;

import com.dynonuggets.refonteimplicaction.auth.error.AuthenticationException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.PUBLIC_KEY_ERROR;
import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.REFRESH_TOKEN_EXPIRED;
import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.emptyStreamIfNull;
import static java.time.Instant.now;
import static java.util.Date.from;

@Slf4j
@Service
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "scopes";

    @Value("${jwt.key_store.password}")
    private String keyStorePassword;
    @Value("${jwt.key_store.name}")
    private String keyStoreName;
    @Value("${jwt.key_store.type}")
    private String keyStoreType;
    @Value("${jwt.key_store.file}")
    private String keyStoreFile;

    private KeyStore keyStore;

    @Value("${jwt.expiration_time}")
    private Long jwtExpirationInMillis;

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

    @PostConstruct
    public void init() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
            final InputStream ressourceAsStream = getClass().getResourceAsStream(keyStoreFile);
            keyStore.load(ressourceAsStream, keyStorePassword.toCharArray());
        } catch (final KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public String generateToken(final Authentication authentication) throws ImplicactionException {
        final List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return generateToken(((User) authentication.getPrincipal()).getUsername(), authorities);
    }

    public String generateToken(final UserDetails userDetails) throws ImplicactionException {
        final List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return generateToken(userDetails.getUsername(), authorities);
    }

    private String generateToken(final String username, final List<String> authorities) {
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, emptyStreamIfNull(authorities).collect(Collectors.joining(",")))
                .signWith(getPrivateKey())
                .setExpiration(from(now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private JwtParser buildJwsParser() throws ImplicactionException {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build();
    }

    public boolean validateToken(final String jwt) throws ImplicactionException {
        try {
            buildJwsParser().parseClaimsJws(jwt);
            return true;
        } catch (final ExpiredJwtException e) {
            throw new AuthenticationException(REFRESH_TOKEN_EXPIRED);
        }
    }

    public String getUsernameFromJwt(final String token) throws ImplicactionException {
        return buildJwsParser()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private PublicKey getPublicKey() throws ImplicactionException {
        try {
            return keyStore.getCertificate(keyStoreName).getPublicKey();
        } catch (final KeyStoreException e) {
            log.error("Exception occured while retrieving public key");
            throw new AuthenticationException(PUBLIC_KEY_ERROR);
        }
    }

    private Key getPrivateKey() throws ImplicactionException {
        try {
            return keyStore.getKey(keyStoreName, keyStorePassword.toCharArray());
        } catch (final KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            log.error("Exception occured while retrieving public key from keystore");
            throw new AuthenticationException(PUBLIC_KEY_ERROR);
        }
    }
}
