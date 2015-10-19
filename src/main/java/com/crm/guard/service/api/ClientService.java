package com.crm.guard.service.api;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.Client;
import com.crm.guard.filter.Filter;
import com.crm.guard.webresult.WebResultTable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ClientService {

    Client findById(String id);

    Client findWithOperator(String id);

    Client findWithContracts(String id);

    WebResultTable<Client> findAllWebResult(Filter filter);

    List<Client> findAll();

    List<Client> findByIds(String clientsIds);

    void saveOrUpdate(Client client);

    @Transactional
    void saveOrUpdate(Collection<AbstractEntity> clients);

    @Transactional(readOnly = true)
    Map<String, Client> getDbClients();

    Client findUnique(String inn, String kpp);

    Client findFromAllIds(String clientId);
}