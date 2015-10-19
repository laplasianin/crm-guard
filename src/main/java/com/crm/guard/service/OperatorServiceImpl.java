package com.crm.guard.service;

import com.crm.guard.dao.ClientDAO;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.User;
import com.crm.guard.service.api.EventTypeService;
import com.crm.guard.service.api.OperatorService;
import com.crm.guard.service.api.UserEventService;
import com.crm.guard.utils.PrincipalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private UserEventService eventService;


    @Override
    public Boolean isMyClient(Client client) {
        if (client == null) {
            return false;
        }

        if (client.getOperator() == null) {
            return false;
        }

        return client.getOperator().getName().equals(PrincipalUtils.principal().getName());
    }

    @Override
    @Transactional
    public void takeToWork(Client client) {
        User operator = PrincipalUtils.principal();
        client.setOperator(operator);

        clientDAO.saveOrUpdate(client);
        eventService.fireOperatorTakeToWorkEvent(client, operator);
    }



    @Override
    @Transactional
    public void kickFromWork(Client client) {
        User operator = client.getOperator();

        client.setOperator(null);
        clientDAO.saveOrUpdate(client);

        eventService.fireOperatorKickFromWorkEvent(client, operator);
    }
}
