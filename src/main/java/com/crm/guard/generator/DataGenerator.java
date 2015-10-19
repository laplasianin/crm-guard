package com.crm.guard.generator;

import com.crm.guard.dao.*;
import com.crm.guard.service.api.ClientGroupService;
import com.crm.guard.service.api.EntityService;
import com.crm.guard.service.api.EventTypeService;
import com.crm.guard.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Transactional()
@Component
public class DataGenerator {

    @Autowired
    private ClientDAO clientDAO;

    @Autowired
    private TotalInvoiceDAO totalInvoiceDAO;

    @Autowired
    private EventTypeDAO eventTypeDAO;

    @Autowired
    private UserEventDAO userEventDAO;

    @Autowired
    private EntityDAO entityDAO;

    @Autowired
    private InvoiceDAO invoiceDAO;

    @Autowired
    private ContractDAO contractDAO;

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GroupMemberDAO groupMemberDAO;

    @Autowired
    private GroupAuthorityDAO groupAuthorityDAO;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private ClientGroupService clientGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityService entityService;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void all() throws IOException {
        testDeleteAll();
        testCreateDefaultData();
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testDeleteAll() throws IOException {
        deleteAll();
    }

//    @PostConstruct
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testCreateDefaultData() throws IOException {
        eventTypeService.createEventTypes();
        clientGroupService.createSecurityGroups();
        userService.createWorkUsers();
        entityService.createEntities();
    }

    private void deleteAll() {

        invoiceDAO.deleteAll();
        contractDAO.deleteAll();
        entityDAO.deleteAll();
        userEventDAO.deleteAll();
        eventTypeDAO.deleteAll();

        groupMemberDAO.deleteAll();
        groupAuthorityDAO.deleteAll();
        groupDAO.deleteAll();
        totalInvoiceDAO.deleteAll();
        clientDAO.deleteAll();
        userDAO.deleteAll();
    }

}
