package com.crm.guard.binder;

import com.crm.guard.entity.Contact;
import com.crm.guard.service.api.ContactService;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;


public class ContactEditor extends PropertyEditorSupport {

    private ContactService contactService;

    public ContactEditor(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text)) {
            setValue(null);
            return;
        }
        setValue(contactService.getContactById(Long.parseLong(text)));
    }

    @Override
    public String getAsText() {
        Contact contact = (Contact) getValue();
        if (contact == null) {
            return null;
        } else {
            return String.valueOf(contact.getId());
        }
    }

}
