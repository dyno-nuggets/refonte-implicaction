package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender mailSender;

    @Value("${app.url}")
    private String contactUrl;
    @Value("${app.contact.mail}")
    private String contactMail;

    @Async
    public void sendMail(NotificationEmailDto notificationEmail) throws ImplicactionException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(contactMail);
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()), true);
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            throw new ImplicactionException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }

    public void sendUserActivationMail(final User user) throws ImplicactionException {
        String messageBody = String.format("Félicitation, vous pouvez désormais vous connecter à l'adresse suivante %s/auth/login", contactUrl);
        NotificationEmailDto notificationEmail = NotificationEmailDto.builder()
                .subject("[Implicaction] Votre compte vient d'être activé")
                .recipient(user.getEmail())
                .body(messageBody)
                .build();
        sendMail(notificationEmail);
    }
}
