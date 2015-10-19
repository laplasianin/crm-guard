package com.realitylabs.guard.generator;

import com.crm.guard.dao.*;
import com.crm.guard.entity.*;
import com.crm.guard.form.contact.ContactUpdateFORM;
import com.crm.guard.service.api.ContactService;
import com.crm.guard.service.api.EventTypeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.jfairy.Fairy;
import org.jfairy.producer.company.Company;
import org.jfairy.producer.person.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:root-context.xml",
        "classpath:spring-security.xml",
        "classpath:spring-servlet.xml"})
@Transactional()
@TransactionConfiguration(defaultRollback = false)
@Component
@WebAppConfiguration
public class DataGenerator {

    private final int SIZE = 20;

    @Autowired
    private ContactService contactService;

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
    private BillDAO billDAO;

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

    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void all() throws IOException {
        testDeleteAll();
        testCreateDefaultData();
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testDeleteAll() throws IOException {
        deleteAll();
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testCreateDefaultData() throws IOException {
        createEventTypes();
        createSecurityGroups();
        createWorkUsers();
        createEntities();
    }

    private void createWorkUsers() {
        Group users = groupDAO.byName("Users");
        if (users == null) {
            throw new IllegalStateException("Нет группы Users");
        }

        Group operators = groupDAO.byName("Operators");
        if (operators == null) {
            throw new IllegalStateException("Нет группы Operators");
        }

        Group admins = groupDAO.byName("Administrators");
        if (admins == null) {
            throw new IllegalStateException("Нет группы Administrators");
        }

        User user = new User();
        user.setName("system");
        user.setPassword("system");
        user.setLastName("system");
        user.setFirstName("system");
        user.setMiddleName("system");
        user.setEnabled(true);
        userDAO.saveUser(user);

        groupMember = new GroupMember();
        groupMember.setGroup(users);
        groupMember.setUser(user);
        groupMemberDAO.saveOrUpdate(groupMember);

        groupMember = new GroupMember();
        groupMember.setGroup(admins);
        groupMember.setUser(user);
        groupMemberDAO.saveOrUpdate(groupMember);

    }

    private void createSecurityGroups() {
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

    private void create() {

        Group users = groupDAO.byName("Users");
        if (users == null) {
            throw new IllegalStateException("Нет группы Users");
        }

        Group operators = groupDAO.byName("Operators");
        if (operators == null) {
            throw new IllegalStateException("Нет группы Operators");
        }

        Group admins = groupDAO.byName("Administrators");
        if (admins == null) {
            throw new IllegalStateException("Нет группы Administrators");
        }

        User operator = userDAO.get("operator");
        if (operator == null) {
            operator = new User();
            operator.setName("operator");
            operator.setLastName("Иванов");
            operator.setFirstName("Оператор");
            operator.setMiddleName("Операторович");
            operator.setPassword("operator");
            operator.setEnabled(true);
            userDAO.saveUser(operator);

            GroupMember groupMember = new GroupMember();
            groupMember.setGroup(users);
            groupMember.setUser(operator);
            groupMemberDAO.saveOrUpdate(groupMember);

            groupMember = new GroupMember();
            groupMember.setGroup(operators);
            groupMember.setUser(operator);
            groupMemberDAO.saveOrUpdate(groupMember);

        }



        Random rnd = new Random();

        Fairy fairy = Fairy.create();
        Company company = fairy.company();

        createEntities();

        List<Entity> entities = entityDAO.getAll();
        if (CollectionUtils.isEmpty(entities)) {
            for (int i = 0; i < nextInt(rnd); i++) {
                entities.add(createEntity(fairy));
            }
        }

        for (int i = 0; i < rnd.nextInt(3); i++) {
            Contract contract = generateContract(rnd, client, entities);
            if (totalInvoice != null) {
                generateRandomInvoice(contract, fairy);
            }
        }
    }

    private void createEventTypes() {
        createEventTypeSaveClient();
        createEventTypeContactSave();
        createEventTypeContactUpdate();
        createEventTypeCall();
        createEventTypeSms();
        createEventTypeEmail();
        createEventTypeTask();
        createEventTypeRegisterEvent();
        createOperatorTakeInWorkEvent();
        createOperatorKickFromWorkEvent();
        createEventClaim();
        createEventDisableNotify();
        createEventDisabled();
        createEventBillDuplication();
    }

    private Contract generateContract(Random rnd, Client client, List<Entity> entities) {
        Contract contract = new Contract();
        contract.setId(client.getId() + "/" + RandomStringUtils.randomAlphabetic(6));
        contract.setClient(client);
        contract.setContractStartDate(new Date());
        if (rnd.nextInt(2) == 1) {
            contract.setContractEndDate(new Date());
        }
        contract.setContractNumber(MessageFormat.format("{0}-{1}-2014",
                RandomStringUtils.randomAlphabetic(2).toUpperCase(),
                RandomStringUtils.randomAlphabetic(nextInt(rnd)).toUpperCase()));
        contract.setEntity(getRndEntity(entities));
        contractDAO.saveOrUpdate(contract);
        return contract;
    }

    private Entity getRndEntity(List<Entity> entities) {
        Random rnd = new Random();

        return entities.get(rnd.nextInt(entities.size()));
    }

    private void createEntities() {
        Entity entity = new Entity();
        entity.setId(1L);
        entity.setName("ООО 1»");
        entity.setShortName("ООО 1");
        entityDAO.saveOrUpdate(entity);
    }

    private void saveClientEvent(User operator, Client client) {
        EventType clientSaveEvent = eventTypeDAO.get(eventTypeService.getClientSaveCode());
        if (clientSaveEvent == null) {
            throw new IllegalStateException("Не найден ивент сохранения клиента");
        }

        UserEvent saveClient = new UserEvent();
        saveClient.setClient(client);
        saveClient.setDescription("Клиент создан");
        saveClient.setEventDate(new Date());
        saveClient.setEventType(clientSaveEvent);
        saveClient.setUser(operator);
        userEventDAO.saveOrUpdate(saveClient);
    }

    private EventType createEventTypeRegisterEvent() {
        EventType registerEvent = createEvent(eventTypeService.getRegisterCode(), "Зарегистрировать событие");
        return registerEvent;
    }

    private EventType createOperatorTakeInWorkEvent() {
        EventType registerEvent = createEvent(eventTypeService.getOperatorTakeClientCode(), "Взять клиента в работу оператором");
        return registerEvent;
    }

    private EventType createOperatorKickFromWorkEvent() {
        EventType registerEvent = createEvent(eventTypeService.getOperatorKickClientCode(), "Отменить работу оператора с клиентом");
        return registerEvent;
    }

    private EventType createEvent(String code, String description) {
        EventType event = eventTypeDAO.get(code);
        if (event == null) {
            event = new EventType();
            event.setName(description);
            event.setCode(code);
            eventTypeDAO.saveOrUpdate(event);
        }
        return event;
    }

    private EventType createEventTypeTask() {
        EventType task = createEvent(eventTypeService.getTaskCode(), "Зарегистрировать задачу");
        return task;
    }

    private EventType createEventTypeEmail() {
        EventType email = createEvent(eventTypeService.getEmailCode(), "Отправка письма по электронной почте");
        return email;
    }

    private EventType createEventTypeSms() {
        EventType sms = createEvent(eventTypeService.getSmsCode(), "Отправка смс");
        return sms;
    }

    private EventType createEventTypeCall() {
        EventType call = createEvent(eventTypeService.getCallCode(), "Звонок клиенту");
        return call;
    }

    private EventType createEventTypeContactUpdate() {
        EventType contactUpdate = createEvent(eventTypeService.getContactUpdateCode(), "Обновление контактного лица");
        return contactUpdate;
    }

    private EventType createEventTypeContactSave() {
        EventType contactSave = createEvent(eventTypeService.getContactSaveCode(), "Сохранение нового контактного лица");
        return contactSave;
    }

    private EventType createEventTypeSaveClient() {
        EventType clientSave = createEvent(eventTypeService.getClientSaveCode(), "Сохранение нового клиента");
        return clientSave;
    }

    private EventType createEventClaim() {
        return createEvent(eventTypeService.getClaimCode(), "Претензия");
    }

    private EventType createEventDisableNotify() {
        return createEvent(eventTypeService.getDisableNotifyCode(), "Уведомление об отключении");
    }

    private EventType createEventDisabled() {
        return createEvent(eventTypeService.getDisabledCode(), "Объект отключен");
    }

    private EventType createEventBillDuplication() {
        return createEvent(eventTypeService.getBillDuplicationCode(), "Продублирован счет");
    }

    private Client createClient(Fairy fairy, Company company) {
        Client client = new Client();
        client.setId(RandomStringUtils.randomAlphabetic(10));
        client.setFullName(company.name());
        client.setInn("INN" + RandomStringUtils.randomNumeric(10));
        client.setMobileNumber(rndMobile());
        client.setOkpo("OKPO" + RandomStringUtils.randomNumeric(6));
        client.setPersonalAccount("L/S" + RandomStringUtils.randomNumeric(15));
        client.setRegistrationAddress(fairy.person().getAddress().toString());
        client.setShortName("OOO " + company.name());
        client.setType(fairy.baseProducer().trueOrFalse() ? ClientType.P : ClientType.I);
        clientDAO.saveOrUpdate(client);
        return client;
    }

    private TotalInvoice createTotalInvoice(Random rnd, Fairy fairy, Client client) {
        TotalInvoice totalInvoice = null;
        if (fairy.baseProducer().trueOrFalse()) {
            totalInvoice = new TotalInvoice();
            totalInvoice.setClient(client);
            totalInvoice.setInvoice(new BigDecimal(rnd.nextInt(100000)));

            totalInvoiceDAO.saveOrUpdate(totalInvoice);
        }
        return totalInvoice;
    }

    private void generateRandomInvoice(Contract contract, Fairy fairy) {
        Random rnd = new Random();

        Invoice invoice = new Invoice();
        invoice.setContract(contract);
        invoice.setDebt(new BigDecimal(fairy.baseProducer().randomBetween(0, 100000)));
        invoice.setInvoiceDate(new Date());
        invoiceDAO.saveOrUpdate(invoice);

        for (int i = 0; i < nextInt(rnd); i++) {
            generateRandomBill(invoice, fairy);
        }
    }

    private void generateRandomBill(Invoice invoice, Fairy fairy) {
        Bill bill1 = new Bill();
        bill1.setBillDate(fairy.dateProducer().randomDateInThePast(1).toDate());
        bill1.setDebt(new BigDecimal(fairy.baseProducer().randomBetween(0,100000)));
        bill1.setDescription(fairy.textProducer().sentence());
        bill1.setInvoice(invoice);
        bill1.setPrice(new BigDecimal(fairy.baseProducer().randomBetween(0,100000)));
        billDAO.saveOrUpdate(bill1);
    }

    private Contact createRandomContact(User system, Client client) {
        Random rnd = new Random();

        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        Person person1 = fairy.person();

        ContactUpdateFORM form = new ContactUpdateFORM();
        form.setOrder(contactService.nextOrder(client));
        form.setBirthDate(person.dateOfBirth().toDate());
        form.setChanger(system);
        form.setClient(client);
        form.setComment(person.username() + " created; email: " + person.email());
        form.setFirstName(person.firstName());
        form.setHomeAddress(person.getAddress().toString());
        form.setLastName(person.lastName());
        form.setMiddleName(person.middleName());
        form.setMobileNumber2(rndMobile());
        form.setMobileNumber3(rndMobile());
        form.setPostAddress(person1.getAddress().toString());
        form.setSex(person.isMale() ? Sex.M : Sex.F);
        form.setStartDate(new Date());
        form.setEndDate(null);
        Contact contact = contactService.saveOrUpdate(form);

        for (int i = 0; i < nextInt(rnd); i++) {  //создаем устаревшие версии контакта contact
            person = fairy.person();
            person1 = fairy.person();

            form.setOrder(contact.getOrder());
            form.setBirthDate(person.dateOfBirth().toDate());
            form.setChanger(system);
            form.setClient(client);
            form.setComment(person.username() + " created; email: " + person.email() + ". OLD");
            form.setFirstName(person.firstName());
            form.setHomeAddress(person.getAddress().toString());
            form.setLastName(person.lastName());
            form.setMiddleName(person.middleName());
            form.setMobileNumber2(rndMobile());
            form.setMobileNumber3(rndMobile());
            form.setPostAddress(person1.getAddress().toString());
            form.setSex(person.isMale() ? Sex.M : Sex.F);
            form.setStartDate(new Date());
            form.setEndDate(new Date());
            contactService.saveOrUpdate(form);
        }

        return contact;
    }

    private int nextInt(Random rnd) {
        return rnd.nextInt(10) + 1;
    }

    private String rndMobile() {
        return RandomStringUtils.randomNumeric(11);
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
