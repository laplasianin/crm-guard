package com.crm.guard.service;

import com.crm.guard.dao.ClientToDoDao;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientToDo;
import com.crm.guard.entity.User;
import com.crm.guard.form.clienttodo.NewToDoFORM;
import com.crm.guard.form.clienttodo.UpdateDatesToDoFORM;
import com.crm.guard.form.clienttodo.UpdateTextToDoFORM;
import com.crm.guard.service.api.ClientToDoService;
import com.crm.guard.utils.PrincipalUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ClientToDoServiceImpl implements ClientToDoService {

    @Autowired
    private ClientToDoDao dao;

    @Override
    @Transactional
    public ClientToDo save(NewToDoFORM form) {
        ClientToDo clientToDo = new ClientToDo();

        clientToDo.setTitle(form.getTitle());
        clientToDo.setDescription(form.getDescription());
        clientToDo.setClient(form.getClient());

        clientToDo.setCreated(new Date());
        clientToDo.setCreatedBy(PrincipalUtils.principal());

        clientToDo.setStart(form.getStart());
        clientToDo.setEnd(form.getEnd());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(form.getStart());
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        clientToDo.setAllDay(calendar.getTime().equals(form.getEnd()));

        dao.saveOrUpdate(clientToDo);
        return clientToDo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientToDo> get(Client client) {
        return dao.get(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientToDo> get(User user) {
        List<ClientToDo> clientToDos = dao.get(user);
        for (ClientToDo clientToDo : clientToDos) {
            Hibernate.initialize(clientToDo.getClient());
        }
        return clientToDos;
    }

    @Override
    @Transactional
    public void update(UpdateDatesToDoFORM form) {
        ClientToDo clientTodo = form.getClientToDo();

        clientTodo.setStart(form.getStart());
        clientTodo.setEnd(form.getEnd());

        dao.saveOrUpdate(clientTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientToDo findById(long id) {
        return dao.get(id);
    }

    @Override
    @Transactional
    public void delete(ClientToDo clientToDo) {
        dao.delete(clientToDo);
    }

    @Override
    @Transactional
    public ClientToDo update(UpdateTextToDoFORM form) {
        ClientToDo clientTodo = form.getClientToDo();

        clientTodo.setTitle(form.getTitle());
        clientTodo.setDescription(form.getDescription());

        dao.saveOrUpdate(clientTodo);
        return clientTodo;
    }

    @Override
    @Transactional
    public ClientToDo finish(ClientToDo form) {
        form.setFinished(new Date());
        form.setFinishedBy(PrincipalUtils.principal());
        dao.saveOrUpdate(form);

        return form;
    }

    @Override
    @Transactional(readOnly = true)
    public long byOperator() {
        return dao.getCount(PrincipalUtils.principal());
    }
}
