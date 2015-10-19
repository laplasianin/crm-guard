package com.crm.guard.dao;

import com.crm.guard.entity.Payment;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class PaymentDAO extends AbstractDao<Payment, Long> {

    public List<Payment> getPayments(String clientCode) {
        Criteria criteria = createCriteria();
        criteria.createAlias("contract", "ctr");
        criteria.createAlias("ctr.client", "cl");

        criteria.add(Restrictions.eq("cl.id", clientCode));
        return criteria.list();
    }

    public List<Payment> findByContractId(String contractId) {
        Criteria criteria = createCriteria();
        criteria.createAlias("contract", "ctr");

        criteria.add(Restrictions.eq("ctr.id", contractId));
        return criteria.list();
    }
}
