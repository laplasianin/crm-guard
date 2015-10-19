package com.crm.guard.dao;

import com.crm.guard.entity.Bill;
import com.crm.guard.entity.ClientGroup;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ClientGroupDAO extends AbstractDao<ClientGroup, String> {
}
