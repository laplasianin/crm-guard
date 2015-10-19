package com.crm.guard.service;

import com.crm.guard.dao.UserEventDAO;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.User;
import com.crm.guard.entity.UserEvent;
import com.crm.guard.form.event.UserEventFORM;
import com.crm.guard.service.api.EventTypeService;
import com.crm.guard.service.api.UserEventService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class UserEventServiceImpl implements UserEventService {

    @Autowired
    private UserEventDAO userEventDAO;

    @Autowired
    private EventTypeService eventTypeService;

    @Override
    @Transactional(readOnly = true)
    public List<UserEvent> getHistory(Client client) {
        if (client == null) {
            return Collections.emptyList();
        }
        return userEventDAO.getHistory(client.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEvent> getHistory(String clientId) {
        if (StringUtils.isEmpty(clientId)) {
            return Collections.emptyList();
        }
        return userEventDAO.getHistory(clientId);
    }

    @Override
    @Transactional
    public UserEvent newEvent(UserEventFORM form) {
        UserEvent event = new UserEvent();
        event.setClient(form.getClient());
        event.setContact(form.getContact());
        event.setDescription(form.getDescription());
        event.setEventDate(new Date());
        event.setEventType(form.getEventType());
        event.setUser(form.getUser());
        userEventDAO.saveOrUpdate(event);
        return event;
    }

    @Override
    @Transactional
    public UserEvent fireOperatorTakeToWorkEvent(Client client, User operator) {
        return newEvent(createOperatorTakeToWorkForm(client, operator));
    }

    @Override
    @Transactional
    public UserEvent fireOperatorKickFromWorkEvent(Client client, User operator) {
        return newEvent(createOperatorKickFromWorkForm(client, operator));
    }

    private UserEventFORM createOperatorTakeToWorkForm(Client client, User operator) {
        UserEventFORM eventFORM = new UserEventFORM();
        eventFORM.setUser(operator);
        eventFORM.setClient(client);
        eventFORM.setDescription(MessageFormat.format("Оператор {0} ({1}) взял в работу клиента {2}",
                operator.fullName(), operator.getName(),
                client.getShortName()));
        eventFORM.setEventDate(new Date());
        eventFORM.setEventType(eventTypeService.findById(eventTypeService.getOperatorTakeClientCode()));
        return eventFORM;
    }

    private UserEventFORM createOperatorKickFromWorkForm(Client client, User operator) {
        UserEventFORM eventFORM = new UserEventFORM();
        eventFORM.setUser(operator);
        eventFORM.setClient(client);
        eventFORM.setDescription(MessageFormat.format("Оператор {0} ({1}) отменил работу с клиентом клиента {2}",
                operator.fullName(), operator.getName(),
                client.getShortName()));
        eventFORM.setEventDate(new Date());
        eventFORM.setEventType(eventTypeService.findById(eventTypeService.getOperatorKickClientCode()));
        return eventFORM;
    }

}
