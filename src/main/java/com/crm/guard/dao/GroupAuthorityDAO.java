package com.crm.guard.dao;

import com.crm.guard.entity.GroupAuthority;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;


@Repository
public class GroupAuthorityDAO extends AbstractDao<GroupAuthority, Long> {

    public List<String> findAuthorities(List<Long> groupsId) {
        if (CollectionUtils.isEmpty(groupsId)) {
            return Collections.emptyList();
        }

        return createCachedCriteria()
                .add(Restrictions.in("group.id", groupsId))
                .setProjection(Projections.distinct(Projections.property("authority")))
                .list();
    }


}
