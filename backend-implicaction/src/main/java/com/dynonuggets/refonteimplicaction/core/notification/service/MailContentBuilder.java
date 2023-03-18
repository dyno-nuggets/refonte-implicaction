package com.dynonuggets.refonteimplicaction.core.notification.service;

import com.dynonuggets.refonteimplicaction.core.notification.model.EmailObject;
import com.dynonuggets.refonteimplicaction.core.notification.model.enums.MailTemplateEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
class MailContentBuilder {

    private static final String TEMPLATE_VAR_NAME = "emailObject";
    private final TemplateEngine templateEngine;

    String build(final MailTemplateEnum templateEnum, final EmailObject emailObject) {
        final Context context = new Context();
        context.setVariable(TEMPLATE_VAR_NAME, emailObject);
        return templateEngine.process(templateEnum.getTemplateName(), context);
    }
}
