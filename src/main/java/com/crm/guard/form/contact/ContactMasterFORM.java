package com.crm.guard.form.contact;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.Contact;
import com.crm.guard.entity.User;
import com.crm.guard.form.base.FORM;

public class ContactMasterFORM implements FORM {

    private Client client;

    private Contact contact;

    private User changer;

    public User getChanger() {
        return changer;
    }

    public void setChanger(User changer) {
        this.changer = changer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
