package com.crm.guard.service.delivery.sender;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.User;
import com.crm.guard.exception.DeliverySendException;
import com.crm.guard.exception.WrongDataBaseValueException;
import com.crm.guard.form.base.FORM;
import com.crm.guard.form.delivery.DeliveryForm;
import com.crm.guard.form.delivery.message.MessageForm;
import com.crm.guard.form.delivery.message.SmsMessageForm;
import com.crm.guard.form.event.UserEventFORM;
import com.crm.guard.service.api.EventTypeService;
import com.crm.guard.service.api.UserEventService;
import com.crm.guard.service.api.UserService;
import com.crm.guard.service.delivery.sender.lib.SmscFacade;
import com.crm.guard.service.delivery.sender.lib.Smscenter;
import com.crm.guard.utils.PrincipalUtils;
import com.crm.guard.utils.collections.transformers.MobileNumberTransformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.apache.commons.collections4.CollectionUtils.collect;
import static org.apache.commons.lang.StringUtils.join;

@Component("SMS")
public class SmsDeliverySender implements DeliverySender {

    @Autowired
    private SmscFacade smscFacade;

    @Autowired
    private UserEventService userEventService;

    @Autowired
    private EventTypeService eventTypeService;

    @Autowired
    private UserService userService;

    @Override
    public void populateForm(MessageForm form) throws DeliverySendException, WrongDataBaseValueException {
        final Client client = form.getClient();

        if (StringUtils.isEmpty(client.getMobileNumber())) {
            throw new WrongDataBaseValueException("У клиента " + client.getFullName() + " не указан номер телефона");
        }

        SmsMessageForm email = (SmsMessageForm) form;
        email.setMobileNumber(client.getMobileNumber());
    }

    @Override
    public void send(DeliveryForm form) throws DeliverySendException, WrongDataBaseValueException {
        String[] result;
        Smscenter smsc = smscFacade.getSmsc();
        if (!form.isContainsTemplateArguments()) {
            String phones = join(collect(form.getMessages(), new MobileNumberTransformer()), ",");

            result = smsc.send_sms(phones, form.getMessages().iterator().next().getBody(), 0, "", "", 3, "", "");
            checkResult(result);
        } else {
            for (MessageForm messageForm : form.getMessages()) {
                Client client = messageForm.getClient();
                result = smsc.send_sms(client.getMobileNumber(), messageForm.getBody(), 0, "", "", 3, "", "");
                checkResult(result);
            }
        }
    }

    @Override
    public void saveEvents(DeliveryForm form) {
        UserEventFORM userEventFORM = (UserEventFORM) getEventForm();
        for (MessageForm messageForm : form.getMessages()) {
            Client client = messageForm.getClient();

            userEventFORM.setClient(client);
            userEventService.newEvent(userEventFORM);
        }
    }

    private void checkResult(String[] result) throws DeliverySendException {
        if (result.length == 2) {
            throw new DeliverySendException("Ошибка СМС-Центра при отправке СМС, " + smscFacade.getError(result[1]));
        }
    }

    @Override
    public MessageForm getBlankForm() {
        return new SmsMessageForm();
    }

    private FORM getEventForm() {
        UserEventFORM userEventFORM = new UserEventFORM();
        userEventFORM.setEventType(eventTypeService.findById(eventTypeService.getSmsCode()));
        userEventFORM.setEventDate(new Date());
        User principal = PrincipalUtils.principal();
        userEventFORM.setUser(principal == null ? userService.system() : principal);
        userEventFORM.setDescription("Отправка СМС");
        return userEventFORM;
    }
}
