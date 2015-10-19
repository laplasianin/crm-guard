package com.crm.guard.service.api;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.Contract;
import com.crm.guard.entity.TotalInvoiceSimple;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ContractService {

    Contract getContractById(String id);

    Contract getContractByIdWithClient(String id);

    void saveOrUpdate(Contract client);

    @Transactional
    void saveOrUpdate(Collection<AbstractEntity> clients);

    String getDisabledString(Client client);

    void clearTotalInvoices();

    List<TotalInvoiceSimple> findTotalInvoices();

    List<Contract> getContractsByClient(String clientId);

    List<Contract> findAll();

    @Transactional(readOnly = true)
    Map<String, Contract> getDbContracts();
}