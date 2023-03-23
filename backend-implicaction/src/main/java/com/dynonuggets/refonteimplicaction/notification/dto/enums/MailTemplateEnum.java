package com.dynonuggets.refonteimplicaction.notification.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum MailTemplateEnum {

    DEFAULT_MAIL_TEMPLATE("mail-template"),
    VALIDATE_SIGNUP_MAIL_TEMPLATE("validate-signup-mail-template");

    private final String templateName;
}
