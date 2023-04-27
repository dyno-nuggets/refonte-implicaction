package com.dynonuggets.refonteimplicaction.notification.service;

import com.dynonuggets.refonteimplicaction.feature.service.FeatureService;
import com.dynonuggets.refonteimplicaction.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.notification.dto.enums.MailTemplateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.dynonuggets.refonteimplicaction.feature.model.properties.enums.FeatureKey.EMAIL_NOTIFICATION;
import static com.dynonuggets.refonteimplicaction.notification.util.NotificationMessages.MAIL_FEATURE_IS_DEACTIVATED_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MailService mailService;
    private final FeatureService featureService;

    public void notify(final EmailDataObject data, final MailTemplateEnum template) {
        if (featureService.isActive(EMAIL_NOTIFICATION)) {
            mailService.sendMail(data, template);
        } else {
            log.info(MAIL_FEATURE_IS_DEACTIVATED_MESSAGE);
        }
    }
}
