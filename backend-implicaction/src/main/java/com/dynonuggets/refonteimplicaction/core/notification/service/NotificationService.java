package com.dynonuggets.refonteimplicaction.core.notification.service;

import com.dynonuggets.refonteimplicaction.core.feature.service.FeatureService;
import com.dynonuggets.refonteimplicaction.core.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.core.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.dynonuggets.refonteimplicaction.core.feature.model.enums.FeatureKey.EMAIL_NOTIFICATION;
import static com.dynonuggets.refonteimplicaction.core.notification.util.NotificationMessages.MAIL_FEATURE_IS_DEACTIVATED_MESSAGE;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final MailService mailService;
    private final FeatureService featureService;

    public <T> void notify(final T input, final NotificationMapper<T> notificationMapper) {
        if (featureService.isActive(EMAIL_NOTIFICATION)) {
            final EmailDataObject emailDataObject = callIfNotNull(input, notificationMapper::formatMailInput);
            mailService.sendMail(notificationMapper.getEmailTemplate(), emailDataObject);
        } else {
            log.info(MAIL_FEATURE_IS_DEACTIVATED_MESSAGE);
        }
    }
}
