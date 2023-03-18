package com.dynonuggets.refonteimplicaction.core.notification.service;

import com.dynonuggets.refonteimplicaction.core.feature.service.FeatureService;
import com.dynonuggets.refonteimplicaction.core.notification.model.EmailObject;
import com.dynonuggets.refonteimplicaction.core.notification.model.enums.MailTemplateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;

import static com.dynonuggets.refonteimplicaction.core.feature.model.enums.FeatureKey.EMAIL_NOTIFICATION;
import static com.dynonuggets.refonteimplicaction.core.notification.util.NotificationMessages.*;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.mail.Message.RecipientType.TO;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    @Value("${app.contact.mail}")
    private String appMail;

    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender mailSender;
    private final FeatureService featureService;

    @Async
    public void sendMail(final MailTemplateEnum templateEnum, final EmailObject emailObject) {
        if (!featureService.isActive(EMAIL_NOTIFICATION)) {
            log.info(MAIL_FEATURE_IS_DEACTIVATED_MESSAGE);
            return;
        }

        try {
            final String allRecipientsJoined = String.join(",", emailObject.getRecipients());
            final MimeMessagePreparator messagePreparator = mimeMessage -> {
                mimeMessage.addRecipients(TO, InternetAddress.parse(allRecipientsJoined));
                final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, UTF_8.name());
                messageHelper.setFrom(appMail);
                messageHelper.setSubject(emailObject.getSubject());
                messageHelper.setText(mailContentBuilder.build(templateEnum, emailObject), true);
            };
            mailSender.send(messagePreparator);
            log.info(format(MAIL_SUCCESS_LOG_MESSAGE, allRecipientsJoined));
        } catch (final MailException e) {
            log.error(MAIL_ERROR_MESSAGE, e);
        }
    }
}
