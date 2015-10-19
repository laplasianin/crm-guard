package com.crm.guard.validator.operator;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.User;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.base.Validator;
import org.springframework.stereotype.Component;


@Component
public class TakeToWorkValidator implements Validator<Client> {

    @Override
    public void validate(Client client, Messages messages) {

        if (client == null) {
            messages.error("Клиент пустой!");
            return;
        }

        User current = client.getOperator();
        if (current != null) {
            messages.error("Клиент уже в работе оператора " + current.fullName());
        }

    }
}
