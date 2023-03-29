package com.dynonuggets.refonteimplicaction.notification.service;

import com.dynonuggets.refonteimplicaction.feature.service.FeatureService;
import com.dynonuggets.refonteimplicaction.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.dynonuggets.refonteimplicaction.core.utils.Utils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.feature.dto.enums.FeatureKey.EMAIL_NOTIFICATION;
import static com.dynonuggets.refonteimplicaction.notification.util.NotificationMessages.MAIL_FEATURE_IS_DEACTIVATED_MESSAGE;

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
