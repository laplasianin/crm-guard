package com.crm.guard.form.contact;

import com.crm.guard.entity.Client;
import com.crm.guard.form.base.FORM;

public class ContactRemoveFORM implements FORM {

    private Long id;

    private Long order;

    private Client client;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
