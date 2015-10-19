package com.crm.guard.service;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.Template;
import com.crm.guard.exception.DeliverySendException;
import com.crm.guard.exception.OperationNotSupportedException;
import com.crm.guard.exception.WrongDataBaseValueException;
import com.crm.guard.form.delivery.DeliveryForm;
import com.crm.guard.form.delivery.message.MessageForm;
import com.crm.guard.service.api.DeliveryFacade;
import com.crm.guard.service.delivery.fabric.DeliveryStrategyFabric;
import com.crm.guard.service.delivery.parse.DeliveryParser;
import com.crm.guard.service.delivery.sender.DeliverySender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DeliveryFacadeImpl implements DeliveryFacade {

    @Autowired
    private DeliveryStrategyFabric strategyFabric;

    @Autowired
    private DeliveryParser deliveryParser;

    private final Logger logger = Logger.getLogger(DeliveryFacadeImpl.class);

    @Override
    public void send(Collection<Client> clients, Template template) throws DeliverySendException, IllegalAccessException, NoSuchFieldException, WrongDataBaseValueException {
        DeliverySender deliverySender;
        try {
            deliverySender = strategyFabric.deliveryGuy(template.getType().name());
        } catch (OperationNotSupportedException e) {
            logger.error("Некорректный тип отправки", e);
            throw new OperationNotSupportedException("Неизвестно, как отправлять сообщения с типом " + template.getType().name());
        }

        // Если нет аргументов - можно не парсить шаблон в цикле
        boolean containsArguments = deliveryParser.containsArguments(template.getTemplate());
        DeliveryForm form = new DeliveryForm();
        form.setContainsTemplateArguments(containsArguments);

        for (Client client : clients) {
            MessageForm newForm = deliverySender.getBlankForm();

            newForm.setClient(client);
            newForm.setSubject(template.getSubject());
            newForm.setBody(getBody(template, containsArguments, client));

            form.add(newForm);
        }
        deliverySender.send(form);
        deliverySender.saveEvents(form);
    }

    private String getBody(Template template, boolean containsArguments, Client client) throws NoSuchFieldException, IllegalAccessException, WrongDataBaseValueException {
        return containsArguments ? deliveryParser.parse(template.getTemplate(), client) : template.getTemplate();
    }
}
