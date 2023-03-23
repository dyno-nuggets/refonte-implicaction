package com.dynonuggets.refonteimplicaction.notification.service;

import com.dynonuggets.refonteimplicaction.core.error.TechnicalException;
import com.dynonuggets.refonteimplicaction.notification.dto.EmailDataObject;
import com.dynonuggets.refonteimplicaction.notification.dto.enums.MailTemplateEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateInputException;

import static com.dynonuggets.refonteimplicaction.notification.util.NotificationMessages.MAIL_TEMPLATE_UNAVAILABLE;

@Slf4j
@Service
@AllArgsConstructor
class MailContentBuilder {

    private static final String TEMPLATE_VAR_NAME = "emailDataObject";
    private final TemplateEngine templateEngine;

    String build(final MailTemplateEnum templateEnum, final EmailDataObject emailDataObject) {
        try {
            final Context context = new Context();
            context.setVariable(TEMPLATE_VAR_NAME, emailDataObject);
            return templateEngine.process(templateEnum.getTemplateName(), context);
        } catch (final TemplateInputException e) {
            throw new TechnicalException(String.format(MAIL_TEMPLATE_UNAVAILABLE, templateEnum.getTemplateName()));
        }
    }
}
