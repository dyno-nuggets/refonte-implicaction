package com.dynonuggets.refonteimplicaction.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
class MailContentBuilder {

    private static final String MAIL_TEMPLATE_FILE = "mail-template";
    private static final String MESSAGE_VAR_NAME = "message";

    private final TemplateEngine templateEngine;

    String build(String message) {
        Context context = new Context();
        context.setVariable(MESSAGE_VAR_NAME, message);
        return templateEngine.process(MAIL_TEMPLATE_FILE, context);
    }
}
