package com.crm.guard.service.api;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientToDo;
import com.crm.guard.entity.User;
import com.crm.guard.form.clienttodo.NewToDoFORM;
import com.crm.guard.form.clienttodo.UpdateDatesToDoFORM;
import com.crm.guard.form.clienttodo.UpdateTextToDoFORM;

import java.util.List;

public interface ClientToDoService {

    ClientToDo save(NewToDoFORM form);

    List<ClientToDo> get(Client client);

    List<ClientToDo> get(User user);

    void update(UpdateDatesToDoFORM form);

    ClientToDo findById(long id);

    void delete(ClientToDo clientToDo);

    ClientToDo update(UpdateTextToDoFORM form);

    ClientToDo finish(ClientToDo form);

    long byOperator();
}