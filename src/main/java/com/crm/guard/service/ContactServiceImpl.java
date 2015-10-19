package com.crm.guard.service;

import com.crm.guard.dao.ContactDAO;
import com.crm.guard.dao.EventTypeDAO;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.Contact;
import com.crm.guard.filter.Filter;
import com.crm.guard.form.contact.ContactMasterFORM;
import com.crm.guard.form.contact.ContactRemoveFORM;
import com.crm.guard.form.contact.ContactUpdateFORM;
import com.crm.guard.form.event.UserEventFORM;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.service.api.ContactService;
import com.crm.guard.service.api.EventTypeService;
import com.crm.guard.service.api.UserEventService;
import com.crm.guard.webresult.WebResultTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EventTypeDAO eventTypeDAO;

    @Autowired
    private UserEventService userEventService;

    @Autowired
    private EventTypeService eventTypeService;

    @Override
    @Transactional
    public Contact saveOrUpdate(ContactUpdateFORM form) {

        if (form.getId() != null) {
            contactDAO.finishPreviousContacts(form);
        }

        Contact old = contactDAO.get(form.getId());

        Contact newContact = new Contact();
        newContact.setBirthDate(form.getBirthDate());
        newContact.setChanger(form.getChanger());
        newContact.setClient(form.getClient());
        newContact.setComment(form.getComment());
        newContact.setFirstName(form.getFirstName());
        newContact.setHomeAddress(form.getHomeAddress());
        newContact.setLastName(form.getLastName());
        newContact.setMiddleName(form.getMiddleName());
        newContact.setMobileNumber2(form.getMobileNumber2());
        newContact.setMobileNumber3(form.getMobileNumber3());
        newContact.setPostAddress(form.getPostAddress());
        newContact.setSex(form.getSex());
        newContact.setStartDate(new Date());

        if (old != null) {
            newContact.setMaster(old.getMaster());
            newContact.setOrder(old.getOrder());
        } else {
            if (contactDAO.countActive(form.getClient().getId()) == 0L) {
                newContact.setMaster(true);
            }
            newContact.setOrder(nextOrder(form.getClient()));
        }

        contactDAO.saveOrUpdate(newContact);

        addUserEvent(form, old, newContact);
        return newContact;

    }

    private void addUserEvent(ContactUpdateFORM form, Contact old, Contact newContact) {
        UserEventFORM userEventFORM = new UserEventFORM();
        userEventFORM.setContact(newContact);
        userEventFORM.setClient(form.getClient());
        userEventFORM.setEventDate(new Date());
        userEventFORM.setUser(form.getChanger());
        if (old != null) {
            userEventFORM.setDescription("Обновление контакного лица " + newContact.getFullName());
            userEventFORM.setEventType(eventTypeDAO.get(eventTypeService.getContactUpdateCode()));
        } else {
            userEventFORM.setDescription("Сохранение нового контакного лица " + newContact.getFullName());
            userEventFORM.setEventType(eventTypeDAO.get(eventTypeService.getContactSaveCode()));
        }
        userEventService.newEvent(userEventFORM);
    }


    @Override
    public Long nextOrder(Client client) {
        Long order = contactDAO.lastOrder(client);
        if (order == null) {
            order = 0L;
        } else {
            order = order + 1;
        }
        return order;
    }

    @Override
    @Transactional
    public void remove(ContactRemoveFORM form) {
        contactDAO.remove(form);
    }

    @Override
    @Transactional(readOnly = true)
    public WebResultTable<Contact> findWebResultById(Filter filter, String id) {
        List<Contact> contacts = contactDAO.getContacts(filter, id);
        return new WebResultTable<Contact>(contacts, 0L);
    }

    @Override
    @Transactional(readOnly = true)
    public Contact getContactById(Long id) {
        return contactDAO.get(id);
    }

    @Override
    @Transactional()
    public void setMaster(ContactMasterFORM form) {
        contactDAO.clearPreviousMaster(form);
        form.getContact().setMaster(true);
        contactDAO.saveOrUpdate(form.getContact());
    }

    @Override
    @Transactional(readOnly = true)
    public Contact findMaster(Client client) {
        return contactDAO.findMaster(client);
    }
}
