package com.crm.guard.binder;

import com.crm.guard.entity.Client;
import com.crm.guard.service.api.ClientService;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class ClientEditor extends PropertyEditorSupport {

    private ClientService clientService;

    public ClientEditor(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
            return;
        }
        setValue(clientService.findById(text));
    }

    @Override
    public String getAsText() {
        Client client = (Client) getValue();
        if (client == null) {
            return null;
        } else {
            return client.getId();
        }
    }
}
