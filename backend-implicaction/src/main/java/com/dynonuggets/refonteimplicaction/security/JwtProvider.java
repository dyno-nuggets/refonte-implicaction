package com.dynonuggets.refonteimplicaction.security;

import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
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

@Service
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "scopes";

    @Value("${jwt.certificate.password}")
    private String keyStorePassword;
    @Value("${jwt.certificate.name}")
    private String certificateName;
    @Value("${jwt.certificate.type}")
    private String keyStoreType;

    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
            // TODO: cacher le nom du certificat à l'aide d'une variable ds le .properties
            InputStream ressourceAsStream = getClass().getResourceAsStream("/implicaction.jks");
            keyStore.load(ressourceAsStream, keyStorePassword.toCharArray());
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String generateToken(Authentication authentication) throws ImplicactionException {
        User principal = (User) authentication.getPrincipal();
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        Date expirationDate = Date.from(Instant.now().plusMillis(jwtExpirationInMillis));
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(getPrivateKey())
                .setExpiration(expirationDate)
                .compact();
    }

    public String generateTokenWithUsername(String username) throws ImplicactionException {
        Date expirationDate = Date.from(Instant.now().plusMillis(jwtExpirationInMillis));
        return Jwts.builder()
                .setSubject(username)
                .signWith(getPrivateKey())
                .setExpiration(expirationDate)
                .compact();
    }

    private JwtParser buildJwtsParser() throws ImplicactionException {
        return Jwts.parserBuilder()
                .setSigningKey(getPublicKey())
                .build();
    }

    public boolean validateToken(String jwt) throws ImplicactionException {
        buildJwtsParser().parseClaimsJws(jwt);
        // Toujours vrai car si erreur => exception lancée
        return true;
    }

    public String getUsernameFromJwt(String token) throws ImplicactionException {
        return buildJwtsParser()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private PublicKey getPublicKey() throws ImplicactionException {
        try {
            return keyStore.getCertificate(certificateName)
                    .getPublicKey();
        } catch (KeyStoreException e) {
            throw new ImplicactionException("Exception occured while retrieving public key");
        }
    }

    private Key getPrivateKey() throws ImplicactionException {
        try {
            // TODO: cachez ce secret que je ne saurais voir
            return keyStore.getKey("implicaction", keyStorePassword.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new ImplicactionException("Exception occured while retrieving public key from keystore");
        }
    }
}
