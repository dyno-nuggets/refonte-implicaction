package com.dynonuggets.refonteimplicaction.core.notification.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum MailTemplateEnum {

    DEFAULT_TEMPLATE("mail-template");

    private final String templateName;
}
