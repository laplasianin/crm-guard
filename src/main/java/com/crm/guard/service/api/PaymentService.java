package com.crm.guard.service.api;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.Payment;

import java.util.Collection;
import java.util.List;

public interface PaymentService {

    List<Payment> getPayments(Client client);

    List<Payment> getPayments(String clientId);

    List<Payment> findByContract(String clientId);

    void save(Payment payment);

    void saveOrUpdate(Collection<AbstractEntity> clients);

    void removeAll();
}