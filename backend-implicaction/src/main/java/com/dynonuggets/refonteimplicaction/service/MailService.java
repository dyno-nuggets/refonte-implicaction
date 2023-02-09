package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
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

import static com.dynonuggets.refonteimplicaction.core.util.Message.GENERIC_MAIL_ERROR_MESSAGE;
import static javax.mail.Message.RecipientType.TO;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender mailSender;

    @Value("${app.contact.mail}")
    private String contactMail;

    @Async
    public void sendMail(final NotificationEmailDto notificationEmail) {
        final MimeMessagePreparator messagePreparator = mimeMessage -> {
            mimeMessage.addRecipients(TO, InternetAddress.parse(String.join(",", notificationEmail.getRecipients())));
            final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            messageHelper.setFrom(contactMail);
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()), true);
        };
        try {
            mailSender.send(messagePreparator);
        } catch (final MailException e) {
            log.error(GENERIC_MAIL_ERROR_MESSAGE);
            throw new RuntimeException(GENERIC_MAIL_ERROR_MESSAGE);
        }
    }
}
