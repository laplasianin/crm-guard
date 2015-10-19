package com.crm.guard.service.api;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.Template;
import com.crm.guard.exception.DeliverySendException;
import com.crm.guard.exception.WrongDataBaseValueException;

import java.util.Collection;

public interface DeliveryFacade {
    void send(Collection<Client> clients, Template template) throws DeliverySendException, IllegalAccessException, NoSuchFieldException, WrongDataBaseValueException;
}
