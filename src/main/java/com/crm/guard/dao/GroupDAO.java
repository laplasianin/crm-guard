package com.crm.guard.dao;

import com.crm.guard.entity.Group;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;


@Repository
public class GroupDAO extends AbstractDao<Group, Long> {

    public Group byName(String name) {
        return (Group) createCachedCriteria()
                .add(Restrictions.eq("name", name)).uniqueResult();
    }

}
