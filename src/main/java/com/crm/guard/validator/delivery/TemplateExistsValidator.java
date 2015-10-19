package com.crm.guard.validator.delivery;

import com.crm.guard.entity.Template;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.base.Validator;
import org.springframework.stereotype.Component;


@Component
public class TemplateExistsValidator implements Validator<Template> {

    @Override
    public void validate(Template form, Messages messages) {
        if (form == null) {
            messages.error("Не задан шаблон");
        }
    }

}
