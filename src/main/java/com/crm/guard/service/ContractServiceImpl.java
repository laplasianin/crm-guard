package com.crm.guard.service;

import com.crm.guard.dao.ContractDAO;
import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.Contract;
import com.crm.guard.entity.TotalInvoiceSimple;
import com.crm.guard.service.api.ContractService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractDAO contractDAO;

    @Override
    public Contract getContractById(String id) {
        return contractDAO.get(id);
    }

    @Override
    public Contract getContractByIdWithClient(String id) {
        Contract contract = contractDAO.get(id);
        if (contract != null) {
            Hibernate.initialize(contract.getClient());
        }
        return contract;
    }

    @Override
    @Transactional
    public void saveOrUpdate(Contract client) {
        contractDAO.saveOrUpdate(client);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Collection<AbstractEntity> clients) {
        for (AbstractEntity client : clients) {
            contractDAO.saveOrUpdate((Contract) client);
        }
    }

    @Override
    public String getDisabledString(Client client) {
        if (client == null) {
            return "";
        }
        if (contractDAO.countActive(client.getId()) > 0) {
            return "";
        }
        Date lastDisabledDate = contractDAO.getLastDisabledDate(client.getId());
        if (lastDisabledDate == null) {
            return "Не заключено ни одного договора";
        }
        return "Отключен " + new SimpleDateFormat("dd.MM.yyyy").format(lastDisabledDate);
    }

    @Override
    @Transactional
    public void clearTotalInvoices() {
        contractDAO.clearTotalInvoices();
    }

    @Override
    public List<TotalInvoiceSimple> findTotalInvoices() {
        return contractDAO.findTotalInvoices();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contract> getContractsByClient(String clientId) {
        List<Contract> contracts = contractDAO.getContracts(clientId);
        for (Contract contract : contracts) {
            Hibernate.initialize(contract.getEntity());
        }
        return contracts;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contract> findAll() {
        return contractDAO.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Contract> getDbContracts() {
        List<Contract> dbContracts = findAll();
        Map<String, Contract> stringContractMap = new HashMap<String, Contract>(dbContracts.size());
        for (Contract dbContract : dbContracts) {
            stringContractMap.put(dbContract.getId(), dbContract);
        }
        return stringContractMap;
    }

}
