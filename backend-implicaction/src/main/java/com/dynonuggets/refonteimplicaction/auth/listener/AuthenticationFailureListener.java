package com.dynonuggets.refonteimplicaction.auth.listener;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Async
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Override
    public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent event) {
        final String username = event.getAuthentication().getName();
        log.info("Echec de l'identification de l'utilisateur {}", username);
    }

}
