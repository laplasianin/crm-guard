package com.crm.guard.form.event;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.Contact;
import com.crm.guard.entity.EventType;
import com.crm.guard.entity.User;
import com.crm.guard.form.base.FORM;

import java.util.Date;

public class UserEventFORM implements FORM {

    private Client client;

    private Contact contact;

    private User user;

    private EventType eventType;

    private String description;

    private Date eventDate;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
