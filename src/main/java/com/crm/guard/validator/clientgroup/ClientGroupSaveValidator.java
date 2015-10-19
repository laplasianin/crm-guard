package com.crm.guard.validator.clientgroup;

import com.crm.guard.entity.ClientGroup;
import com.crm.guard.service.api.ClientGroupService;
import com.crm.guard.validator.base.Messages;
import com.crm.guard.validator.base.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ClientGroupSaveValidator implements Validator<ClientGroup> {

    @Autowired
    private ClientGroupService clientGroupService;


    @Override
    public void validate(ClientGroup group, Messages messages) {

        if (group == null) {
            messages.error("Форма пустая!");
            return;
        }

        if (StringUtils.isEmpty(group.getCode())) {
            messages.error("Код не заполнен");
            return;
        }

        if (StringUtils.isEmpty(group.getDescription())) {
            messages.error("Описание не заполнено");
            return;
        }


        if (clientGroupService.findByCode(group.getCode()) != null) {
            messages.error("Уже сушествует группа с идентификатором " + group.getCode());
            return;
        }

    }
}
