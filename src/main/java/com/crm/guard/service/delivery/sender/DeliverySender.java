package com.crm.guard.service.delivery.sender;

import com.crm.guard.exception.DeliverySendException;
import com.crm.guard.exception.WrongDataBaseValueException;
import com.crm.guard.form.delivery.DeliveryForm;
import com.crm.guard.form.delivery.message.MessageForm;

public interface DeliverySender {

    void populateForm(MessageForm form) throws DeliverySendException, WrongDataBaseValueException;

    void send(DeliveryForm form) throws DeliverySendException, WrongDataBaseValueException;

    void saveEvents(DeliveryForm form);

    MessageForm getBlankForm();

}
