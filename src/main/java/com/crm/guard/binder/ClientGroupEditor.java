package com.crm.guard.binder;

import com.crm.guard.entity.ClientGroup;
import com.crm.guard.service.api.ClientGroupService;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class ClientGroupEditor extends PropertyEditorSupport {

    private ClientGroupService clientGroupService;

    public ClientGroupEditor(ClientGroupService clientGroupService) {
        this.clientGroupService = clientGroupService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
            return;
        }
        setValue(clientGroupService.findByCode(text));
    }

    @Override
    public String getAsText() {
        ClientGroup group = (ClientGroup) getValue();
        if (group == null) {
            return null;
        } else {
            return group.getCode();
        }
    }
}
