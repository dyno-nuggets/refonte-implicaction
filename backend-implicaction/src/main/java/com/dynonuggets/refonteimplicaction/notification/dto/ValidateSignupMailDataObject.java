package com.dynonuggets.refonteimplicaction.notification.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidateSignupMailDataObject extends EmailDataObject {

    private final String validationLink;
    private final String username;

    @Builder
    public ValidateSignupMailDataObject(final String subject, final List<String> recipients, final String validationLink, final String username) {
        super(subject, recipients);
        this.validationLink = validationLink;
        this.username = username;
    }
}
