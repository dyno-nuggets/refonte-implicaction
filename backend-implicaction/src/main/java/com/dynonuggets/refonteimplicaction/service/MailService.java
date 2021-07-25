package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicitActionException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender mailSender;

    void sendMail(NotificationEmailDto notificationEmail) throws ImplicitActionException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("implicaction@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            throw new ImplicitActionException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }
}
