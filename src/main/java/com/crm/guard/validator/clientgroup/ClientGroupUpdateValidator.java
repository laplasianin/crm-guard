package com.crm.guard.validator.clientgroup;

import com.crm.guard.entity.ClientGroup;
import com.crm.guard.service.api.ClientGroupService;
import com.crm.guard.validator.base.Messages;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ClientGroupUpdateValidator {

    @Autowired
    private ClientGroupService clientGroupService;


    public void validate(ClientGroup group, String description, Messages messages) {

        if (group == null) {
            messages.error("Группа пустая!");
            return;
        }

        if (StringUtils.isEmpty(description)) {
            messages.error("Описание не заполнено");
            return;
        }
    }
}
