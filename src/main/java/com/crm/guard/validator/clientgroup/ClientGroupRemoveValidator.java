package com.crm.guard.validator.clientgroup;

import com.crm.guard.entity.ClientGroup;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.base.Validator;
import org.springframework.stereotype.Component;


@Component
public class ClientGroupRemoveValidator implements Validator<ClientGroup> {


    @Override
    public void validate(ClientGroup group, Messages messages) {

        if (group == null) {
            messages.error("Группа ненайдена в базе данных");
            return;
        }

    }
}
