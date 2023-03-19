package com.dynonuggets.refonteimplicaction.core.notification.service;

import com.dynonuggets.refonteimplicaction.core.error.TechnicalException;
import com.dynonuggets.refonteimplicaction.core.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.core.notification.dto.enums.MailTemplateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;

import static com.dynonuggets.refonteimplicaction.core.notification.util.NotificationMessages.MAIL_SUCCESS_LOG_MESSAGE;
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

    @Async
    public void sendMail(final MailTemplateEnum templateEnum, final EmailDataObject emailDataObject) {
        final String allRecipientsJoined = String.join(",", emailDataObject.getRecipients());
        final MimeMessagePreparator messagePreparator = mimeMessage -> {
            try {
                mimeMessage.addRecipients(TO, InternetAddress.parse(allRecipientsJoined));
                final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, UTF_8.name());
                messageHelper.setFrom(appMail);
                messageHelper.setSubject(emailDataObject.getSubject());
                messageHelper.setText(mailContentBuilder.build(templateEnum, emailDataObject), true);
            } catch (final TechnicalException e) {
                log.error(e.getMessage());
                throw e;
            }
        };
        mailSender.send(messagePreparator);
        log.info(format(MAIL_SUCCESS_LOG_MESSAGE, allRecipientsJoined));
    }
}
