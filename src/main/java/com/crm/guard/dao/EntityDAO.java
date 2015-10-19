package com.crm.guard.dao;

import com.crm.guard.entity.Entity;
import com.crm.guard.entity.UserEvent;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class EntityDAO extends AbstractDao<Entity, Long> {

}
