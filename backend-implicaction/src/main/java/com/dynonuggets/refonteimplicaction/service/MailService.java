package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.NotificationEmailDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.dynonuggets.refonteimplicaction.util.Constants.ACTIVATION_ENDPOINT;
import static com.dynonuggets.refonteimplicaction.util.Constants.IMPLICACTION_MAIL_ADDRESS;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final MailContentBuilder mailContentBuilder;
    private final JavaMailSender mailSender;

    @Async
    public void sendMail(NotificationEmailDto notificationEmail) throws ImplicactionException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(IMPLICACTION_MAIL_ADDRESS);
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent!!");
        } catch (MailException e) {
            throw new ImplicactionException("Exception occurred when sending mail to " + notificationEmail.getRecipient());
        }
    }

    public void sendUserActivationMail(final String activationKey, final User user) throws ImplicactionException {
        NotificationEmailDto notificationEmail = NotificationEmailDto.builder()
                .subject("Please activate your account")
                .recipient(user.getEmail())
                .body("Thank you for signing up to Implicaction, please click on the below url to activate your account : "
                        + ACTIVATION_ENDPOINT + "/" + activationKey)
                .build();
        sendMail(notificationEmail);
    }
}
