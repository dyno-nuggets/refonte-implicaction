package com.dynonuggets.refonteimplicaction.dto;

import com.dynonuggets.refonteimplicaction.core.notification.dto.EmailDataObject;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class NotificationEmailDtoData extends EmailDataObject {
    @Builder
    public NotificationEmailDtoData(final String subject, final List<String> recipients, final String body) {
        super(subject, recipients);
        this.body = body;
    }

    private final String body;
}
