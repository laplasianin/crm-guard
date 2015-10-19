package com.crm.guard.dao;

import com.crm.guard.entity.File;
import com.crm.guard.filter.CriteriaAliasBuilder;
import com.crm.guard.filter.Filter;
import com.crm.guard.filter.SearchField;
import com.crm.guard.filter.SearchType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileDAO extends AbstractDao<File, Long>  {

    public List<File> getFiles(Filter filter, String clientId) {

        CriteriaAliasBuilder aliasBuilder = new CriteriaAliasBuilder();

        Criteria criteria = createCriteria()
                .add(Restrictions.eq("client.id", clientId));

        addSorts(filter, criteria, aliasBuilder);
        addSearches(filter, aliasBuilder, criteria);

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

    public File getFile(String clientId, String fileName) {

        Criteria criteria = createCriteria()
                .add(Restrictions.eq("client.id", clientId))
                .add(Restrictions.eq("name", fileName));

        List list = criteria.list();
        return list.isEmpty() ? null : (File) list.get(0);
    }
}
