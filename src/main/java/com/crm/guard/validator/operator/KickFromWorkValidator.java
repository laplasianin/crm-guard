package com.crm.guard.validator.operator;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.User;
import com.crm.guard.utils.PrincipalUtils;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.base.Validator;
import org.springframework.stereotype.Component;


@Component
public class KickFromWorkValidator implements Validator<Client> {

    @Override
    public void validate(Client client, Messages messages) {

        if (client == null) {
            messages.error("Клиент пустой!");
            return;
        }

        User current = client.getOperator();
        if (current == null) {
            messages.error("Клиент и так не в работе ни одного оператора ");
            return;
        }

        if (!current.getName().equals(PrincipalUtils.principal().getName())) {
            messages.error("Вы не можете снять с работы оператора, это не в вашей ответственности");
            return;
        }

    }
}
