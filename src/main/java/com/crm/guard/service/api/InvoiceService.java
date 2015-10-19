package com.crm.guard.service.api;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.Invoice;

import java.util.Collection;
import java.util.List;

public interface InvoiceService {

    List<Invoice> getInvoices(Client client);

    List<Invoice> getInvoices(String clientId);

    List<Invoice> findByContract(String clientId);

    void save(Invoice invoice);

    void saveOrUpdate(Collection<AbstractEntity> clients);

    void removeAll();
}