package com.dynonuggets.refonteimplicaction.auth.mapper;

import com.dynonuggets.refonteimplicaction.auth.dto.ValidateSignupMailDataObject;
import com.dynonuggets.refonteimplicaction.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.notification.dto.enums.MailTemplateEnum;
import com.dynonuggets.refonteimplicaction.notification.mapper.NotificationMapper;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.AUTH_ACCOUNT_VERIFICATION_URI;
import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.AUTH_BASE_URI;
import static com.dynonuggets.refonteimplicaction.notification.dto.enums.MailTemplateEnum.VALIDATE_SIGNUP_MAIL_TEMPLATE;
import static java.util.List.of;

@Component
@RequiredArgsConstructor
public class EmailValidationNotificationMapper implements NotificationMapper<UserModel> {

    @Value("${app.url}")
    private String apiUrl;

    @Override
    public EmailDataObject formatMailInput(final UserModel input) {
        return ValidateSignupMailDataObject.builder()
                .username(input.getUsername())
                .validationLink(apiUrl + AUTH_BASE_URI + AUTH_ACCOUNT_VERIFICATION_URI.replace("{activationKey}", input.getActivationKey()))
                .subject("[Implicaction] veuillez confirmer votre adresse email")
                .recipients(of(input.getEmail()))
                .build();
    }

    @Override
    public MailTemplateEnum getEmailTemplate() {
        return VALIDATE_SIGNUP_MAIL_TEMPLATE;
    }
}
