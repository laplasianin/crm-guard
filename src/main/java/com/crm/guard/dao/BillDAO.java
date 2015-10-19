package com.crm.guard.dao;

import com.crm.guard.entity.Bill;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class BillDAO extends AbstractDao<Bill, Long> {
    public List<Bill> findByInvoiceId(Long invoiceId) {
        return createCriteria()
                .createAlias("invoice", "in")
                .add(Restrictions.eq("in.id", invoiceId)).list();
    }
}
