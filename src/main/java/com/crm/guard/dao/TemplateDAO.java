package com.crm.guard.dao;

import com.crm.guard.entity.Template;
import com.crm.guard.filter.CriteriaAliasBuilder;
import com.crm.guard.filter.Filter;
import com.crm.guard.filter.SearchField;
import com.crm.guard.filter.SearchType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TemplateDAO extends AbstractDao<Template, Long> {

    public List<Template> find(Filter filter) {
        Criteria criteria = createCriteria();
        CriteriaAliasBuilder aliasBuilder = new CriteriaAliasBuilder();

        addSorts(filter, criteria, aliasBuilder);
        addSearches(filter, aliasBuilder, criteria);
        criteria.setFirstResult(filter.getOffset())
                .setMaxResults(filter.getLimit());

        return criteria.list();
    }

    private void addSearches(Filter filter, CriteriaAliasBuilder aliasBuilder, Criteria criteria) {
        for (SearchField searchField : filter.getSearchFields()) {
            if (searchField.getField().equals("_ALL_")) {
                criteria.add(Restrictions.or(
                        getRestriction(new SearchField("name", SearchType.LIKE_CASE_INSENSITIVE, searchField.getValue()),
                                criteria,
                                aliasBuilder)
                ));
            }else {
                addSearchField(criteria, searchField, aliasBuilder);
            }
        }
    }

    public Long count(Filter filter) {
        CriteriaAliasBuilder aliasBuilder = new CriteriaAliasBuilder();

        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.countDistinct("id"));

        addSearches(filter, aliasBuilder, criteria);

        return (Long) criteria.uniqueResult();
    }
}
