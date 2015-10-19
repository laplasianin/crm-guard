package com.crm.guard.dao;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientToDo;
import com.crm.guard.entity.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class ClientToDoDao extends AbstractDao<ClientToDo, Long> {
    public List<ClientToDo> get(Client client) {
        return createCriteria().add(Restrictions.eq("client", client)).list();
    }

    public List<ClientToDo> get(User user) {
        return createQuery("from ClientToDo todo " +
                "where todo.finished is null and todo.start < :now " +
                " and (todo.client.operator.name = :name OR todo.client.operator is null)")
                .setTimestamp("now", new Timestamp(new Date().getTime()))
                .setString("name", user.getName())
                .list();
    }

    public long getCount(User user) {
        return (Long) createQuery("select count(*) from ClientToDo todo " +
                "where todo.finished is null and todo.start < :now " +
                " and (todo.client.operator.name = :name OR todo.client.operator is null)")
                .setTimestamp("now", new Timestamp(new Date().getTime()))
                .setString("name", user.getName())
                .uniqueResult();
    }
}
