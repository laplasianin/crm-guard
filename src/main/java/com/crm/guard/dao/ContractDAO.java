package com.crm.guard.dao;

import com.crm.guard.entity.Contract;
import com.crm.guard.entity.TotalInvoiceSimple;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public class ContractDAO extends AbstractDao<Contract, String> {

    public Long countActive(String clientId) {
        return (Long) createCriteria()
                .add(Restrictions.eq("client.id", clientId))
                .add(Restrictions.isNull("contractEndDate"))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    public Date getLastDisabledDate(String clientId) {
        return (Date) createCriteria()
                .add(Restrictions.eq("client.id", clientId))
                .add(Restrictions.isNotNull("contractEndDate"))
                .addOrder(Order.desc("contractEndDate"))
                .setProjection(Projections.property("contractEndDate")).setMaxResults(1).uniqueResult();
    }

    public void clearTotalInvoices() {
        getSession().createQuery("update Contract c set c.totalInvoice = null").executeUpdate();
    }

    public List<TotalInvoiceSimple> findTotalInvoices() {
        return getSession().createQuery(
                "select new com.crm.guard.entity.TotalInvoiceSimple(client.id, sum(c.totalInvoice)) " +
                        "from com.crm.guard.entity.Contract c " +
                        "GROUP BY c.client.id " +
                        "HAVING sum(c.totalInvoice) > 0").list();
    }

    public List<Contract> getContracts(String clientCode) {
        Criteria criteria = createCriteria();
        criteria.createAlias("client", "cl");

        criteria.add(Restrictions.eq("cl.id", clientCode));
        return criteria.list();
    }
}
