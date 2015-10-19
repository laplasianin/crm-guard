package com.crm.guard.validator.event;

import com.crm.guard.form.event.UserEventFORM;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.base.Validator;
import org.springframework.stereotype.Component;


@Component
public class UserEventCreateValidator implements Validator<UserEventFORM> {

    @Override
    public void validate(UserEventFORM form, Messages messages) {
        // TODO
    }

}
