package com.dynonuggets.refonteimplicaction.auth.mapper;

import com.dynonuggets.refonteimplicaction.auth.dto.ValidateSignupMailDataObject;
import com.dynonuggets.refonteimplicaction.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.notification.mapper.NotificationMapper;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.AUTH_ACCOUNT_VERIFICATION_URI;
import static com.dynonuggets.refonteimplicaction.auth.util.AuthUris.AUTH_BASE_URI;
import static com.dynonuggets.refonteimplicaction.notification.dto.enums.MailTemplateEnum.VALIDATE_SIGNUP_MAIL_TEMPLATE;
import static com.dynonuggets.refonteimplicaction.user.utils.UserTestUtils.generateRandomUser;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

class EmailValidationNotificationMapperTest {

    @Spy
    NotificationMapper<UserModel> mapper = new EmailValidationNotificationMapper();

    private final String API_URL = "http://adresse";

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(mapper, "apiUrl", API_URL);
    }

    @Test
    void formatMailInput() {
        // given
        final UserModel user = generateRandomUser();
        final ValidateSignupMailDataObject expectedEmailData = ValidateSignupMailDataObject.builder()
                .username(user.getUsername())
                .validationLink(API_URL + AUTH_BASE_URI + AUTH_ACCOUNT_VERIFICATION_URI.replace("{activationKey}", user.getActivationKey()))
                .subject("[Implicaction] veuillez confirmer votre adresse email")
                .recipients(of(user.getEmail()))
                .build();

        // when
        final EmailDataObject actualEmailData = mapper.formatMailInput(user);

        // then
        assertThat(actualEmailData)
                .isExactlyInstanceOf(ValidateSignupMailDataObject.class)
                .usingRecursiveComparison().ignoringFields()
                .isEqualTo(expectedEmailData);

    }

    @Test
    void getEmailTemplate() {
        assertThat(mapper.getEmailTemplate()).isEqualTo(VALIDATE_SIGNUP_MAIL_TEMPLATE);
    }
}
