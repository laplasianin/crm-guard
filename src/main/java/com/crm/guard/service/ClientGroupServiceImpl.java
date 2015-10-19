package com.crm.guard.service;

import com.crm.guard.dao.ClientGroupDAO;
import com.crm.guard.dao.GroupAuthorityDAO;
import com.crm.guard.dao.GroupDAO;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.ClientGroup;
import com.crm.guard.entity.Group;
import com.crm.guard.entity.GroupAuthority;
import com.crm.guard.service.api.ClientGroupService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class ClientGroupServiceImpl implements ClientGroupService {

    @Autowired
    private ClientGroupDAO clientGroupDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private GroupAuthorityDAO groupAuthorityDAO;

    @Transactional(readOnly = true)
    @Override
    public ClientGroup findByCode(String code) {
        return clientGroupDAO.get(code);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientGroup> findAll() {
        return clientGroupDAO.getAll(); // TODO cached
    }

    @Override
    @Transactional
    public void saveOrUpdate(ClientGroup form) {
        clientGroupDAO.saveOrUpdate(form);
    }

    @Override
    @Transactional
    public void remove(ClientGroup group) {
        clientGroupDAO.delete(group);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientGroup findWithClients(String code) {
        ClientGroup byCode = findByCode(code);
        Hibernate.initialize(byCode.getClients());
        return byCode;
    }

    @Override
    @Transactional
    public boolean addClient(ClientGroup group, Client client) {
        if (!group.getClients().contains(client)) {
            group.getClients().add(client);
            clientGroupDAO.saveOrUpdate(group);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean removeClient(ClientGroup group, Client client) {
        Iterator<Client> iterator = group.getClients().iterator();

        while (iterator.hasNext()) {
            Client clientIt = iterator.next();
            if (clientIt.getId().equals(client.getId())) {
                iterator.remove();
                clientGroupDAO.saveOrUpdate(group);
                return true;
            }
        }


        return false;
    }

    @Transactional
    @Override
    public void createSecurityGroups() {
        if (groupDAO.count() == 0) {
            Group admins = new Group();
            admins.setName("Administrators");

            groupDAO.saveOrUpdate(admins);

            Group operators = new Group();
            operators.setName("Operators");

            groupDAO.saveOrUpdate(operators);

            Group users = new Group();
            users.setName("Users");

            groupDAO.saveOrUpdate(users);

            GroupAuthority adminsAuthorities = new GroupAuthority();
            adminsAuthorities.setGroup(admins);
            adminsAuthorities.setAuthority("ROLE_ADMIN");
            groupAuthorityDAO.saveOrUpdate(adminsAuthorities);

            GroupAuthority operatorsAuthorities = new GroupAuthority();
            operatorsAuthorities.setGroup(operators);
            operatorsAuthorities.setAuthority("ROLE_OPERATOR");
            groupAuthorityDAO.saveOrUpdate(operatorsAuthorities);

            GroupAuthority usersAuthorities = new GroupAuthority();
            usersAuthorities.setGroup(users);
            usersAuthorities.setAuthority("ROLE_USER");
            groupAuthorityDAO.saveOrUpdate(usersAuthorities);
        }

    }

}
