package com.crm.guard.dao;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.Contact;
import com.crm.guard.filter.CriteriaAliasBuilder;
import com.crm.guard.filter.Filter;
import com.crm.guard.filter.SearchField;
import com.crm.guard.form.contact.ContactMasterFORM;
import com.crm.guard.form.contact.ContactRemoveFORM;
import com.crm.guard.form.contact.ContactUpdateFORM;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;


@Repository
public class ContactDAO extends AbstractDao<Contact, Long> {

    public List<Contact> getContacts(Filter filter, String clientId) {
        CriteriaAliasBuilder aliasBuilder = new CriteriaAliasBuilder();

        Criteria criteria = createCriteria()
                .add(Restrictions.eq("client.id", clientId))
                .add(Restrictions.isNull("endDate"));

        addSorts(filter, criteria, aliasBuilder);
        addSearches(filter, aliasBuilder, criteria);

        return criteria.list();
    }

    private void addSearches(Filter filter, CriteriaAliasBuilder aliasBuilder, Criteria criteria) {
        for (SearchField searchField : filter.getSearchFields()) {
            Object value = searchField.getValue();
            if (searchField.getField().equals("_ALL_")) {
                criteria.add(Restrictions.or(
                        like("lastName", (String) value),
                        like("firstName", (String) value),
                        like("middleName", (String) value),
                        like("mobileNumber2", (String) value),
                        like("mobileNumber3", (String) value)
                ));
            }else {
                addSearchField(criteria, searchField, aliasBuilder);
            }
        }
    }

    public Long countActive(String clientId) {
        return (Long) createCriteria()
                .add(Restrictions.eq("client.id", clientId))
                .add(Restrictions.isNull("endDate"))
                .setProjection(Projections.rowCount()).uniqueResult();
    }

    public void finishPreviousContacts(ContactUpdateFORM form) {
        createQuery("update Contact c set c.endDate = :endDate where c.client = :client and c.order = :order and c.endDate is null")
                .setDate("endDate", new Date())
                .setParameter("client", form.getClient())
                .setLong("order", form.getOrder()).executeUpdate();
    }

    public void clearPreviousMaster(ContactMasterFORM form) {
        createQuery("update Contact c set c.master = :master where c.endDate is null and c.client = :client and c.master = :oldMaster")
                .setBoolean("master", false)
                .setParameter("client", form.getClient())
                .setBoolean("oldMaster", true).executeUpdate();
    }

    public void remove(ContactRemoveFORM form) {
        createQuery("delete from Contact c where c.client = :client and c.order = :order")
                .setParameter("client", form.getClient())
                .setLong("order", form.getOrder()).executeUpdate();
    }

    public Long lastOrder(Client client) {
        return (Long) createCriteria()
                .add(Restrictions.eq("client", client))
                .setProjection(Projections.max("order"))
                .uniqueResult();
    }

    public Contact findMaster(Client client) {
        List list = createCriteria()
                .add(Restrictions.eq("client", client))
                .add(Restrictions.eq("master", true))
                .add(Restrictions.isNull("endDate"))
                .list();
        return CollectionUtils.isEmpty(list) ? null : (Contact) list.get(0);
    }
}
