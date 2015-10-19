package com.crm.guard.dao;

import com.crm.guard.entity.IntegrationAudit;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static org.hibernate.criterion.Restrictions.isNotNull;
import static org.hibernate.criterion.Restrictions.isNull;


@Repository
public class IntegrationAuditDAO extends AbstractDao<IntegrationAudit, Long> {

    public boolean hasUncompleted() {
        return (Long) createQuery("select count(*) from IntegrationAudit i " +
                "where i.completed is null and i.terminated is null").uniqueResult() > 0;

    }

    public List<IntegrationAudit> getUncompleted() {
        List<IntegrationAudit> list = createQuery("from IntegrationAudit i " +
                "where i.completed is null and i.terminated is null").list();
        return list;
    }

    public IntegrationAudit getLastUnCompleted() {
        List<IntegrationAudit> uncompleted = createCriteria().add(isNull("completed")).add(isNull("terminated")).addOrder(Order.desc("created")).setMaxResults(1).list();

        return uncompleted.isEmpty() ? null : uncompleted.get(0);
    }

    public IntegrationAudit getLastCompleted() {
        List<IntegrationAudit> completed = createCriteria().add(isNotNull("completed")).addOrder(Order.desc("completed")).setMaxResults(1).list();
        List<IntegrationAudit> terminated = createCriteria().add(isNotNull("terminated")).addOrder(Order.desc("terminated")).setMaxResults(1).list();

        if (completed.isEmpty() && terminated.isEmpty()) {
            throw new IllegalStateException("Запуск интеграции с 1с не проводился, обратитесь к администратору");
        } else if (!completed.isEmpty() && !terminated.isEmpty()) {
            Date completedTime = completed.get(0).getCompleted();
            Date terminatedTime = terminated.get(0).getTerminated();
            if (completedTime.after(terminatedTime)){
                return completed.get(0);
            }
            throw new IllegalStateException("Последняя интеграция с 1с была завершена с ошибками, обратитесь к администратору");
        } else if (!completed.isEmpty()) {
            return completed.get(0);
        }
        throw new IllegalStateException("Последняя интеграция с 1с была завершена с ошибками, обратитесь к администратору");
    }

    public IntegrationAudit getLastCompletedOrTerminated() {
        List<IntegrationAudit> completed = createCriteria().add(isNotNull("completed")).addOrder(Order.desc("completed")).setMaxResults(1).list();
        List<IntegrationAudit> terminated = createCriteria().add(isNotNull("terminated")).addOrder(Order.desc("terminated")).setMaxResults(1).list();

        if (completed.isEmpty() && terminated.isEmpty()) {
            return null;
        } else if (!completed.isEmpty() && !terminated.isEmpty()) {
            Date completedTime = completed.get(0).getCompleted();
            Date terminatedTime = terminated.get(0).getTerminated();
            if (completedTime.after(terminatedTime)){
                return completed.get(0);
            } else {
                return terminated.get(0);
            }
        } else if (!completed.isEmpty()) {
            return completed.get(0);
        }
        return terminated.get(0);
    }
}
