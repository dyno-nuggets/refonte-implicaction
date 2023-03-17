package com.dynonuggets.refonteimplicaction.core.security;

import com.dynonuggets.refonteimplicaction.auth.error.AuthenticationException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.auth.error.AuthErrorResult.PUBLIC_KEY_ERROR;

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
    public void init() {
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
            final InputStream ressourceAsStream = getClass().getResourceAsStream(keyStoreFile);
            keyStore.load(ressourceAsStream, keyStorePassword.toCharArray());
        } catch (final KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            log.error(e.getMessage());
        }
    }

    public String generateToken(final Authentication authentication) throws ImplicactionException {
        final User principal = (User) authentication.getPrincipal();
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        final Date expirationDate = Date.from(Instant.now().plusMillis(jwtExpirationInMillis));
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(getPrivateKey())
                .setExpiration(expirationDate)
                .compact();
    }

    public String generateTokenWithUsername(final String username) throws ImplicactionException {
        final Date expirationDate = Date.from(Instant.now().plusMillis(jwtExpirationInMillis));
        return Jwts.builder()
                .setSubject(username)
                .signWith(getPrivateKey())
                .setExpiration(expirationDate)
                .compact();
    }

    private JwtParser buildJwsParser() throws ImplicactionException {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build();
    }

    public boolean validateToken(final String jwt) throws ImplicactionException {
        buildJwsParser().parseClaimsJws(jwt);
        // Toujours vrai car si erreur => exception lancée
        return true;
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
