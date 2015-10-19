package com.crm.guard.service.api;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.User;
import com.crm.guard.entity.UserEvent;
import com.crm.guard.form.event.UserEventFORM;

import java.util.List;

public interface UserEventService {

    List<UserEvent> getHistory(Client client);

    List<UserEvent> getHistory(String clientId);

    UserEvent newEvent(UserEventFORM form);

    UserEvent fireOperatorTakeToWorkEvent(Client client, User operator);

    UserEvent fireOperatorKickFromWorkEvent(Client client, User operator);
}