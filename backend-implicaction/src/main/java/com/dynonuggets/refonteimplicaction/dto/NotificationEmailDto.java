package com.dynonuggets.refonteimplicaction.dto;

import com.dynonuggets.refonteimplicaction.core.notification.model.EmailObject;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class NotificationEmailDto extends EmailObject {
    @Builder
    public NotificationEmailDto(final String subject, final List<String> recipients, final String body) {
        super(subject, recipients);
        this.body = body;
    }

    private final String body;
}
