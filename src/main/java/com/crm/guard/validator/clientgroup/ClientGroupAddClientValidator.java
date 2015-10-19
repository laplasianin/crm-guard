package com.crm.guard.validator.clientgroup;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientGroup;
import com.crm.guard.validator.base.Messages;
import org.springframework.stereotype.Component;


@Component
public class ClientGroupAddClientValidator  {



    public void validate(ClientGroup group, Client client, Messages messages) {

        if (group == null) {
            messages.error("Группа не найдена в базе данных");
            return;
        }

        if (client == null) {
            messages.error("Клиент не найден в базе данных");
            return;
        }



    }
}
