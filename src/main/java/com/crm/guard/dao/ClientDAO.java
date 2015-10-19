package com.crm.guard.dao;

import com.crm.guard.entity.Client;
import com.crm.guard.filter.CriteriaAliasBuilder;
import com.crm.guard.filter.Filter;
import com.crm.guard.filter.SearchField;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.hibernate.criterion.Restrictions.eq;


@Repository
public class ClientDAO extends AbstractDao<Client, String> {

    public List<Client> find(Filter filter) {
        Criteria criteria = createCriteria();
        CriteriaAliasBuilder aliasBuilder = new CriteriaAliasBuilder();

        addSorts(filter, criteria, aliasBuilder);
        addSearch(filter, criteria, aliasBuilder);

        criteria.setFirstResult(filter.getOffset())
                .setMaxResults(filter.getLimit());

        criteria.setProjection(Projections.distinct(Projections.property("id")));
        List<Long> ids = criteria.list();

        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        criteria = createCriteria();
        aliasBuilder = new CriteriaAliasBuilder();
        criteria.add(Restrictions.in("id", ids));
        addSorts(filter, criteria, aliasBuilder);

        return criteria.list();
    }

    private void addSearch(Filter filter, Criteria criteria, CriteriaAliasBuilder aliasBuilder) {
        for (SearchField searchField : filter.getSearchFields()) {
            if (searchField.getField().equals("_ALL_")) {
                String value = (String) searchField.getValue();
                criteria.add(Restrictions.or(
                        like("shortName", value),
                        like("fullName", value),
                        like("inn", value),
                        like("okpo", value),
                        like("personalAccount", value),
                        like("personalAccount", value),
                        like("registrationAddress", value)
                        ));
            } else {
                addSearchField(criteria, searchField, aliasBuilder);
            }
        }
    }

    private Criterion in(String path, Collection values) {
        return Restrictions.in(path, values);
    }

    public Long count(Filter filter) {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.countDistinct("id"));
        CriteriaAliasBuilder aliasBuilder = new CriteriaAliasBuilder();
        addSearch(filter, criteria, aliasBuilder);

        return (Long) criteria.uniqueResult();
    }

    public Client findUnique(String inn, String kpp) {
        return (Client) createCriteria().add(eq("inn", inn)).add(eq("okpo", kpp)).uniqueResult();
    }

    public Client findFromAllIds(String clientId) {
        return (Client) createCriteria().add(like("allIds", clientId)).uniqueResult();
    }
}
