package com.dynonuggets.refonteimplicaction.auth.mapper;

import com.dynonuggets.refonteimplicaction.auth.dto.ValidateSignupMailDataObject;
import com.dynonuggets.refonteimplicaction.core.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.core.notification.dto.enums.MailTemplateEnum;
import com.dynonuggets.refonteimplicaction.core.notification.mapper.NotificationMapper;
import com.dynonuggets.refonteimplicaction.core.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.AUTH_ACCOUNT_VERIFICATION_URI;
import static com.dynonuggets.refonteimplicaction.core.notification.dto.enums.MailTemplateEnum.VALIDATE_SIGNUP_MAIL_TEMPLATE;
import static java.util.List.of;

@Component
@RequiredArgsConstructor
public class EmailValidationNotificationMapper implements NotificationMapper<User> {

    @Value("${app.url}")
    private String appUrl;

    @Override
    public EmailDataObject formatMailInput(final User input) {
        return ValidateSignupMailDataObject.builder()
                .username(input.getUsername())
                .validationLink(appUrl + AUTH_ACCOUNT_VERIFICATION_URI.replace("{activationKey}", input.getActivationKey()))
                .subject("[Implicaction] veuillez confirmer votre adresse email")
                .recipients(of(input.getEmail()))
                .build();
    }

    @Override
    public MailTemplateEnum getEmailTemplate() {
        return VALIDATE_SIGNUP_MAIL_TEMPLATE;
    }
}
