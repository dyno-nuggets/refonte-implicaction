package com.dynonuggets.refonteimplicaction.security;

import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream ressourceAsStream = getClass().getResourceAsStream("/implicaction.jks");
            keyStore.load(ressourceAsStream, ".fxG3KPB.".toCharArray()); // TODO: cachez ce secret que je ne saurais voir
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String generateToken(Authentication authentication) throws ImplicactionException {
        User principal =  (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private Key getPrivateKey() throws ImplicactionException {
        try {
            return keyStore.getKey("implicaction", ".fxG3KPB.".toCharArray()); // TODO: cachez ce secret que je ne saurais voir
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new ImplicactionException("Exception occured while retrieving public key from keystore");
        }
    }
}
