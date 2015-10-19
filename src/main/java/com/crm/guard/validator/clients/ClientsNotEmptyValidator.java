package com.crm.guard.validator.clients;

import com.crm.guard.entity.Client;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.base.Validator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;


@Component
public class ClientsNotEmptyValidator implements Validator<Collection<Client>> {

    @Override
    public void validate(Collection<Client> clients, Messages messages) {
        if (CollectionUtils.isEmpty(clients)) {
            messages.error("Клиенты не выбраны");
        }
    }

}
