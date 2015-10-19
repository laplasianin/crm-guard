package com.crm.guard.form.delivery;

import com.crm.guard.form.delivery.message.MessageForm;

import java.util.ArrayList;
import java.util.Collection;

public class DeliveryForm {

    private Collection<MessageForm> messages = new ArrayList<MessageForm>();
    private boolean containsTemplateArguments;

    public void add(MessageForm messageForm) {
        messages.add(messageForm);
    }

    public Collection<MessageForm> getMessages() {
        return messages;
    }

    public void setMessages(Collection<MessageForm> messages) {
        this.messages = messages;
    }

    public void setContainsTemplateArguments(boolean containsTemplateArguments) {
        this.containsTemplateArguments = containsTemplateArguments;
    }

    public boolean isContainsTemplateArguments() {
        return containsTemplateArguments;
    }
}
