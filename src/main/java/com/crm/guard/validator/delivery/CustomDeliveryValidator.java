package com.crm.guard.validator.delivery;

import com.crm.guard.entity.Template;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.base.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


@Component
public class CustomDeliveryValidator implements Validator<Template> {

    @Override
    public void validate(Template form, Messages messages) {
        if (form == null) {
            messages.error("Не задан шаблон");
            return;
        }
        if (form.getType() == null) {
            messages.error("Не задан тип");
        }
        if (StringUtils.isEmpty(form.getSubject())) {
            messages.error("Не задан заголовок сообщения");
        }
        if (StringUtils.isEmpty(form.getTemplate())) {
            messages.error("Не задан текс сообщения");
        }
    }

}
