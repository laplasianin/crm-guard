package com.crm.guard.validator.contact;

import com.crm.guard.form.contact.ContactUpdateFORM;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.base.Validator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class ContactUpdateValidator implements Validator<ContactUpdateFORM> {

    @Override
    public void validate(ContactUpdateFORM contactUpdateFORM, Messages messages) {
        if (StringUtils.isEmpty(contactUpdateFORM.getFirstName()) && StringUtils.isEmpty(contactUpdateFORM.getLastName())) {
            messages.error("Не указано контактное имя");
        }

        if (StringUtils.isEmpty(contactUpdateFORM.getMobileNumber2()) && StringUtils.isEmpty(contactUpdateFORM.getMobileNumber3())) {
            messages.error("Не указано ни одного контактного номера");
        }

        if (contactUpdateFORM.getChanger() == null) {
            messages.error("Не найден пользователь");
        }
    }

}
