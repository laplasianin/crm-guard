package com.crm.guard.service;

import com.crm.guard.dao.ClientDAO;
import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.Client;
import com.crm.guard.filter.Filter;
import com.crm.guard.service.api.ClientService;
import com.crm.guard.webresult.WebResultTable;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDAO clientDAO;

    @Override
    public Client findById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return clientDAO.get(id);
    }

    @Override
    public Client findWithOperator(String id) {
        Client client = findById(id);
        if (client != null) {
            Hibernate.initialize(client.getOperator());
        }
        return client;
    }

    @Override
    public Client findWithContracts(String id) {
        Client client = findById(id);
        if (client != null) {
            Hibernate.initialize(client.getContracts());
        }
        return client;
    }

    @Override
    public WebResultTable<Client> findAllWebResult(Filter filter) {
        List<Client> all = clientDAO.find(filter);
        return new WebResultTable<Client>(all, clientDAO.count(filter));
    }

    @Override
    public List<Client> findAll() {
        return clientDAO.getAll();
    }

    @Override
    public List<Client> findByIds(String ids) {
        final ArrayList<Client> clients = new ArrayList<Client>();

        if (StringUtils.isEmpty(ids)) {
            return clients;
        }

        return clientDAO.get(Arrays.asList(ids.trim().split(",")));
    }

    @Override
    @Transactional
    public void saveOrUpdate(Client client) {
        clientDAO.saveOrUpdate(client);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Collection<AbstractEntity> clients) {
        for (AbstractEntity client : clients) {
            clientDAO.saveOrUpdate((Client) client);
        }
    }

    @Override
     @Transactional(readOnly = true)
     public Map<String, Client> getDbClients() {
        List<Client> dbContracts = findAll();
        Map<String, Client> stringContractMap = new HashMap<String, Client>(dbContracts.size());
        for (Client dbContract : dbContracts) {
            stringContractMap.put(dbContract.getId(), dbContract);
        }
        return stringContractMap;
    }

    @Override
    public Client findUnique(String inn, String kpp) {
        return clientDAO.findUnique(inn, kpp);
    }

    @Override
    public Client findFromAllIds(String clientId) {
        return clientDAO.findFromAllIds(clientId);
    }

}
