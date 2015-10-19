package com.crm.guard.service.delivery.parse;

import com.crm.guard.entity.Client;
import com.crm.guard.entity.Contact;
import com.crm.guard.exception.WrongDataBaseValueException;
import com.crm.guard.service.api.ContactService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import static com.crm.guard.service.delivery.parse.DeliveryPatterns.values;
import static com.crm.guard.utils.ReflectionUtils.getFieldByName;

@Component
public class DeliveryParser {

    @Autowired
    private ContactService contactService;

    private static final Logger logger = LoggerFactory.getLogger(DeliveryParser.class);

    public boolean containsArguments(String text) {
        for (DeliveryPatterns pattern : values()) {
            if (text.contains("%" + pattern.getPath() + "%")) {
                return true;
            }
        }

        return false;
    }

    public String parse(String text, Client client) throws NoSuchFieldException, IllegalAccessException, WrongDataBaseValueException {
        Contact masterContact = null;
        ST st = new ST(text, "%".charAt(0), "%".charAt(0));

        for (DeliveryPatterns pattern : values()) {
            String key = pattern.getPath();
            String value = null;
            switch (pattern.getEntityType()) {
                case client:
                    value = String.valueOf(getFieldByName(client, pattern.getEntityField()));
                    break;
                case contact:
                    if (masterContact == null) {
                        masterContact = getMasterContact(client);
                        if (masterContact == null) {
                            throw new WrongDataBaseValueException("У клиента " + client.getShortName() +
                                    " не заполнено ответственное лицо");
                        }
                    }
                    if (pattern.getEntityField().equals("fullName")) {
                        value = masterContact.getFullName();
                    } else {
                        value = String.valueOf(getFieldByName(masterContact, pattern.getEntityField()));
                    }
                    break;
            }
            if (StringUtils.isEmpty(value)) {
                throw new WrongDataBaseValueException("У клиента " + client.getShortName() +
                        " не заполнено поле " + pattern.getDescription());
            }
            st.add(key, value);
        }
        return st.render();
    }

    private Contact getMasterContact(Client client) {
        return contactService.findMaster(client);
    }
}
