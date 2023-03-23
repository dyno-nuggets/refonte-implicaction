package com.dynonuggets.refonteimplicaction.notification.mapper;

import com.dynonuggets.refonteimplicaction.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.notification.dto.enums.MailTemplateEnum;

public interface NotificationMapper<T> {
    EmailDataObject formatMailInput(T input);

    MailTemplateEnum getEmailTemplate();

}
