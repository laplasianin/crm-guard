package com.crm.guard.validator.base;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Collections;

@XmlAccessorType(XmlAccessType.FIELD)
public class Messages extends ArrayList<Message> {

    public Messages() {
        super();
    }

    public Messages(BindingResult result) {
        super();
        populate(result);
    }


    public void error(String message) {
        add(Message.error(message));
    }

    public void warning(String message) {
        add(Message.warning(message));
    }

    public void info(String message) {
        add(Message.info(message));
    }

    public void success(String message) {
        add(Message.success(message));
    }

    public void error(String code, String message) {
        add(Message.error(code, message));
    }

    public void warning(String code, String message) {
        add(Message.warning(code, message));
    }

    public void info(String code, String message) {
        add(Message.info(code, message));
    }

    public boolean hasErrors() {
        for (Message message : this)
            if (message.getType().equals(Type.error))
                return true;
        return false;
    }

    public boolean noErrors() {
        return !hasErrors();
    }

    public int errors() {
        int errors = 0;
        for (Message message : this)
            errors += message.getType().equals(Type.error) ? 1 : 0;
        return errors;
    }

    public int warnings() {
        int warnings = 0;
        for (Message message : this)
            warnings += message.getType().equals(Type.warning) ? 1 : 0;
        return warnings;
    }

    public void populate(BindingResult result) {
        for (ObjectError error : result.getAllErrors()) {
            add(Message.error(error.getDefaultMessage()));
        }
    }

    public String toString() {
        Collections.sort(this);
        StringBuilder messages = new StringBuilder();
        messages.append("Сообщения:");
        for (Message message : this) {
            messages.append("\n  ");
            messages.append(message);
        }
        if (size() == 0)
            messages.append(" Ошибок нет");
        return messages.toString();
    }
}