package com.crm.guard.dao;

import com.crm.guard.entity.UserEvent;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserEventDAO extends AbstractDao<UserEvent, Long> {

    public List<UserEvent> getHistory(String clientId) {
        List<UserEvent> events = createCriteria()
                .add(Restrictions.eq("client.id", clientId))
                .addOrder(Order.desc("eventDate"))
                .list();

        for (UserEvent event : events) {
            Hibernate.initialize(event.getContact());
        }
        return events;
    }
}
