package com.crm.guard.service.api;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.Contact;
import com.crm.guard.filter.Filter;
import com.crm.guard.form.contact.ContactMasterFORM;
import com.crm.guard.form.contact.ContactRemoveFORM;
import com.crm.guard.form.contact.ContactUpdateFORM;
import com.crm.guard.webresult.WebResultTable;

public interface ContactService {

    Contact saveOrUpdate(ContactUpdateFORM form);

    Long nextOrder(Client client);

    void remove(ContactRemoveFORM form);

    WebResultTable<Contact> findWebResultById(Filter filter, String id);

    Contact getContactById(Long id);

    void setMaster(ContactMasterFORM form);

    Contact findMaster(Client client);

}