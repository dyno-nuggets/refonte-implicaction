package com.dynonuggets.refonteimplicaction.core.notification.mapper;

import com.dynonuggets.refonteimplicaction.core.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.core.notification.dto.enums.MailTemplateEnum;

public interface NotificationMapper<T> {
    EmailDataObject formatMailInput(T input);

    MailTemplateEnum getEmailTemplate();

}
