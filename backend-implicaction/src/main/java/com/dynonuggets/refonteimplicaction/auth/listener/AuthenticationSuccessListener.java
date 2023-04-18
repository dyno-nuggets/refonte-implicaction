package com.dynonuggets.refonteimplicaction.auth.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Async
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {
        final String username = event.getAuthentication().getName();
        log.info("Identification de l'utilisateur {} effectuée avec succès", username);
    }
}
