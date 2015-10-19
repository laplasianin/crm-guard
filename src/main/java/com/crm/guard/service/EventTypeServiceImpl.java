package com.crm.guard.service;

import com.crm.guard.dao.EventTypeDAO;
import com.crm.guard.dao.UserEventDAO;
import com.crm.guard.entity.Client;
import com.crm.guard.entity.EventType;
import com.crm.guard.entity.User;
import com.crm.guard.entity.UserEvent;
import com.crm.guard.service.api.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class EventTypeServiceImpl implements EventTypeService {

    @Autowired
    private EventTypeDAO eventTypeDAO;

    @Autowired
    private UserEventDAO userEventDAO;

    @Override
    @Transactional(readOnly = true)
    public EventType findById(String id) {
        return eventTypeDAO.get(id);
    }

    @Override
    public String getClientSaveCode() {
        return "clientSave";
    }

    @Override
    public String getContactSaveCode() {
        return "contactSave";
    }

    @Override
    public String getContactUpdateCode() {
        return "contactUpdate";
    }

    @Override
    public String getCallCode() {
        return "call";
    }

    @Override
    public String getSmsCode() {
        return "sms";
    }

    @Override
    public String getEmailCode() {
        return "email";
    }

    @Override
    public String getTaskCode() {
        return "task";
    }

    @Override
    public String getRegisterCode() {
        return "event";
    }

    @Override
    public String getOperatorTakeClientCode() {
        return "takeInWork";
    }

    @Override
    public String getOperatorKickClientCode() {
        return "kickFromWork";
    }

    @Override
    public String getClaimCode() {
        return "CLAIM";
    }

    @Override
    public String getDisableNotifyCode() {
        return "DISABLE_NOTIFY";
    }

    @Override
    public String getDisabledCode() {
        return "DISABLED";
    }

    @Override
    public String getBillDuplicationCode() {
        return "BILL_DUPLICATION";
    }

    @Override
    @Transactional
    public void createEventTypes() {
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

    private EventType createEventTypeRegisterEvent() {
        EventType registerEvent = createEvent(getRegisterCode(), "Зарегистрировать событие");
        return registerEvent;
    }

    private EventType createOperatorTakeInWorkEvent() {
        EventType registerEvent = createEvent(getOperatorTakeClientCode(), "Взять клиента в работу оператором");
        return registerEvent;
    }

    private EventType createOperatorKickFromWorkEvent() {
        EventType registerEvent = createEvent(getOperatorKickClientCode(), "Отменить работу оператора с клиентом");
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
        EventType task = createEvent(getTaskCode(), "Зарегистрировать задачу");
        return task;
    }

    private EventType createEventTypeEmail() {
        EventType email = createEvent(getEmailCode(), "Отправка письма по электронной почте");
        return email;
    }

    private EventType createEventTypeSms() {
        EventType sms = createEvent(getSmsCode(), "Отправка смс");
        return sms;
    }

    private EventType createEventTypeCall() {
        EventType call = createEvent(getCallCode(), "Звонок клиенту");
        return call;
    }

    private EventType createEventTypeContactUpdate() {
        EventType contactUpdate = createEvent(getContactUpdateCode(), "Обновление контактного лица");
        return contactUpdate;
    }

    private EventType createEventTypeContactSave() {
        EventType contactSave = createEvent(getContactSaveCode(), "Сохранение нового контактного лица");
        return contactSave;
    }

    private EventType createEventTypeSaveClient() {
        EventType clientSave = createEvent(getClientSaveCode(), "Сохранение нового клиента");
        return clientSave;
    }

    private EventType createEventClaim() {
        return createEvent(getClaimCode(), "Претензия");
    }

    private EventType createEventDisableNotify() {
        return createEvent(getDisableNotifyCode(), "Уведомление об отключении");
    }

    private EventType createEventDisabled() {
        return createEvent(getDisabledCode(), "Объект отключен");
    }

    private EventType createEventBillDuplication() {
        return createEvent(getBillDuplicationCode(), "Продублирован счет");
    }
}
