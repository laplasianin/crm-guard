package com.crm.guard.service.api;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientGroup;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClientGroupService {

    ClientGroup findByCode(String code);

    List<ClientGroup> findAll();

    void saveOrUpdate(ClientGroup form);

    void remove(ClientGroup group);

    ClientGroup findWithClients(String group);

    boolean addClient(ClientGroup group, Client client);

    boolean removeClient(ClientGroup withClients, Client client);

    @Transactional
    void createSecurityGroups();
}