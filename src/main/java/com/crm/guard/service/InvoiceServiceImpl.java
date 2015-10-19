package com.crm.guard.service;

import com.crm.guard.dao.InvoiceDAO;
import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.Invoice;
import com.crm.guard.service.api.InvoiceService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDAO invoiceDAO;

    @Override
    public List<Invoice> getInvoices(Client client) {
        if (client == null) {
            return Collections.emptyList();
        }
        return getInvoices(client.getId());
    }

    @Override
    public List<Invoice> getInvoices(String clientId) {
        if (StringUtils.isEmpty(clientId)) {
            return Collections.emptyList();
        }
        return invoiceDAO.getInvoices(clientId);
    }

    @Override
    public List<Invoice> findByContract(String contractId) {
        if (StringUtils.isEmpty(contractId)) {
            return Collections.emptyList();
        }
        List<Invoice> invoices = invoiceDAO.findByContractId(contractId);
        for (Invoice invoice : invoices) {
            Hibernate.initialize(invoice.getContract());
        }
        return invoices;
    }

    @Override
    @Transactional()
    public void save(Invoice invoice) {
        invoiceDAO.saveOrUpdate(invoice);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Collection<AbstractEntity> clients) {
        for (AbstractEntity invoice : clients) {
            invoiceDAO.saveOrUpdate((Invoice) invoice);
        }
    }

    @Override
    @Transactional()
    public void removeAll() {
        invoiceDAO.deleteAll();
    }
}
