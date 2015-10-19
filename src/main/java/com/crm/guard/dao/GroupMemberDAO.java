package com.crm.guard.dao;

import com.crm.guard.entity.GroupMember;
import com.crm.guard.entity.User;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class GroupMemberDAO extends AbstractDao<GroupMember, Long> {

    public List<Long> findGroupsId(User user) {
        return createCachedCriteria()
                .add(Restrictions.eq("user", user))
                .createAlias("group", "gr")
                .setProjection(Projections.distinct(Projections.property("gr.id")))
                .list();
    }
}
