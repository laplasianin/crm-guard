package com.crm.guard.dao;

import com.crm.guard.entity.AbstractEntity;
import com.crm.guard.filter.*;
import com.crm.guard.filter.Filter;
import com.crm.guard.utils.ConvertionUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.crm.guard.utils.ConvertionUtils.*;

public abstract class AbstractDao<T extends AbstractEntity, PK extends java.io.Serializable> {

    @Autowired
    private SessionFactory sessionFactory;

    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public void deleteAll() {
        List<T> list = createCriteria().list();
        for (T t : list) {
            delete(t);
        }
    }

    protected SimpleExpression like(String path, String value) {
        return Restrictions.like(path, value, MatchMode.ANYWHERE).ignoreCase();
    }

    public void delete(PK id) {
        getSession().delete(get(id));
    }
    public Long count() {
        return (Long) createCriteria().setProjection(Projections.rowCount()).uniqueResult();
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public T get(PK id) {

        return (T) createCriteria().add(Restrictions.like("id", id))
                .uniqueResult();
    }

    public List<T> get(List<PK> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<T>();
        }
        return (List<T>) createCriteria().add(Restrictions.in("id", ids))
                .list();
    }

    public void change(T entity) {
        getSession().update(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return createCriteria().list();
    }

    protected Criteria createCriteria() {
        return getSession().createCriteria(getEntityClass());
    }

    protected Criteria createCachedCriteria() {
        return getSession().createCriteria(getEntityClass()).setCacheable(true).setCacheMode(CacheMode.NORMAL);
    }

    protected Query createQuery(String hql) {
        return sessionFactory.getCurrentSession().createQuery(hql);
    }

    protected Query createCachedQuery(String hql) {
        return sessionFactory.getCurrentSession().createQuery(hql).setCacheable(true).setCacheMode(CacheMode.NORMAL);
    }


    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected void addSearchFields(Filter filter, Criteria criteria, CriteriaAliasBuilder aliasBuilder) {
        for (SearchField searchField : filter.getSearchFields()) {
            criteria.add(getRestriction(searchField, criteria, aliasBuilder));
        }
    }

    protected void addSearchField(Criteria criteria, SearchField searchField, CriteriaAliasBuilder aliasBuilder) {
        criteria.add(getRestriction(searchField, criteria, aliasBuilder));
    }

    /**
     *
     * examples
     * <br>
     *   getRestriction(new SearchField("totalInvoice.client.code", SearchType.EQUAL, searchField.getValue()), criteria, aliasBuilder),
     * <br>
     *   getRestriction(new SearchField("totalInvoice.client.contacts.lastName", SearchType.EQUAL, searchField.getValue()), criteria, aliasBuilder)
     *
     * **/
    protected Criterion getRestriction(SearchField searchField, Criteria criteria, CriteriaAliasBuilder aliasBuilder) {
        String property = aliasBuilder.build(criteria, searchField.getField());

        Object value = searchField.getValue();
        Number numberValue = parseNumber(property, String.valueOf(value));
        String stringValue = String.valueOf(value);
        List<String> stringCollection = toStringCollection(stringValue);
        List<Long> longCollection = toLongCollection(stringCollection);
        Timestamp timestampValue = ConvertionUtils.toTimestamp(value);

        switch (searchField.getRestriction()) {
            case EQUAL:
               return Restrictions.eq(property, value);
            case NOT_EQUAL:
                return Restrictions.ne(property, value);
            case LIKE:
            case LIKE_CASE_INSENSITIVE:
            return Restrictions.like(property, (String)value, MatchMode.ANYWHERE).ignoreCase();
            case NULL:
               return Restrictions.isNull(property);
            case NOT_NULL:
               return Restrictions.isNotNull(property);
            case GREATER_THAN_OR_EQUAL_TO:
            case GREATER_THAN:
                if (numberValue != null) {
                    return Restrictions.ge(property, numberValue);
                } else if (timestampValue != null) {
                    return Restrictions.ge(property, timestampValue);
                }
                break;
            case LESS_THAN_OR_EQUAL_TO:
            case LESS_THAN:
                if (numberValue != null) {
                    return Restrictions.le(property, numberValue);
                } else if (timestampValue != null) {
                    return Restrictions.le(property, timestampValue);
                }
                break;
            case IN:
                if (searchField.getField().equals("groups.code")) {
                    return Restrictions.in(property, stringCollection);
                } else {
                    return Restrictions.in(property, longCollection);
                }

        }
        return null;
    }

    protected void addSorts(com.crm.guard.filter.Filter filter, Criteria criteria, CriteriaAliasBuilder aliasBuilder) {
        for (SortField sortField : filter.getSortFields()) {
            String property = aliasBuilder.build(criteria, sortField.getField());
            Order order = sortField.getSort().equals(SortType.ASC) ?
                    Order.asc(property): Order.desc(property);
            criteria.addOrder(order);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
