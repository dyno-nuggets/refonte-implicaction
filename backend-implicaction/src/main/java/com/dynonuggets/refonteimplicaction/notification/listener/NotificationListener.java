package com.dynonuggets.refonteimplicaction.notification.listener;

import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.core.event.UserCreatedEvent;
import com.dynonuggets.refonteimplicaction.notification.dto.ValidateSignupMailDataObject;
import com.dynonuggets.refonteimplicaction.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.AUTH_ACCOUNT_VERIFICATION_URI;
import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.AUTH_BASE_URI;
import static com.dynonuggets.refonteimplicaction.notification.dto.enums.MailTemplateEnum.VALIDATE_SIGNUP_MAIL_TEMPLATE;
import static com.google.common.collect.ImmutableList.of;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    @Value("${app.url}")
    private String apiUrl;

    private final NotificationService notificationService;

    @Async
    @EventListener
    public void handleUserCreated(final UserCreatedEvent uce) {
        final UserModel user = uce.getUser();
        final ValidateSignupMailDataObject build = ValidateSignupMailDataObject.builder()
                .username(user.getUsername())
                .validationLink(apiUrl + AUTH_BASE_URI + AUTH_ACCOUNT_VERIFICATION_URI.replace("{activationKey}", user.getActivationKey()))
                .subject("[Implicaction] veuillez confirmer votre adresse email")
                .recipients(of(user.getEmail()))
                .build();
        notificationService.notify(build, VALIDATE_SIGNUP_MAIL_TEMPLATE);
    }
}
