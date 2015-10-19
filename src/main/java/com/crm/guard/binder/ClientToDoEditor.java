package com.crm.guard.binder;

import com.crm.guard.entity.ClientToDo;
import com.crm.guard.service.api.ClientToDoService;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class ClientToDoEditor extends PropertyEditorSupport {

    private ClientToDoService clientToDoService;

    public ClientToDoEditor(ClientToDoService clientToDoService) {
        this.clientToDoService = clientToDoService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
            return;
        }
        setValue(clientToDoService.findById(Long.parseLong(text)));
    }

    @Override
    public String getAsText() {
        ClientToDo clientToDo = (ClientToDo) getValue();
        if (clientToDo == null) {
            return null;
        } else {
            return String.valueOf(clientToDo.getId());
        }
    }
}
