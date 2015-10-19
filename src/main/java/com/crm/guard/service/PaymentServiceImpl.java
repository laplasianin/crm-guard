package com.crm.guard.service;

import com.crm.guard.dao.PaymentDAO;
import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.Payment;
import com.crm.guard.service.api.PaymentService;
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
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentDAO paymentDAO;

    @Override
    public List<Payment> getPayments(Client client) {
        if (client == null) {
            return Collections.emptyList();
        }
        return getPayments(client.getId());
    }

    @Override
    public List<Payment> getPayments(String clientId) {
        if (StringUtils.isEmpty(clientId)) {
            return Collections.emptyList();
        }
        return paymentDAO.getPayments(clientId);
    }

    @Override
    public List<Payment> findByContract(String contractId) {
        if (StringUtils.isEmpty(contractId)) {
            return Collections.emptyList();
        }
        List<Payment> payments = paymentDAO.findByContractId(contractId);
        for (Payment payment : payments) {
            Hibernate.initialize(payment.getContract());
        }
        return payments;
    }

    @Override
    @Transactional()
    public void save(Payment payment) {
        paymentDAO.saveOrUpdate(payment);
    }

    @Override
    @Transactional
    public void saveOrUpdate(Collection<AbstractEntity> clients) {
        for (AbstractEntity payment : clients) {
            paymentDAO.saveOrUpdate((Payment) payment);
        }
    }

    @Override
    @Transactional()
    public void removeAll() {
        paymentDAO.deleteAll();
    }
}
