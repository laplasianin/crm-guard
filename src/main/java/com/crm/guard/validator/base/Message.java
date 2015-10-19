package com.crm.guard.validator.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Comparable<Message> {

    private final Type type;

    private final String message;

    private final String code;

    public Message(Type type, String message) {
        this(type, null, message);
    }

    public Message(Type type, String code, String message) {
        this.type = type;
        this.message = message;
        this.code = code;
    }

    public static Message error(String message) {
        return new Message(Type.error, message);
    }

    public static Message info(String message) {
        return new Message(Type.info, message);
    }

    public static Message success(String message) {
        return new Message(Type.success, message);
    }

    public static Message warning(String message) {
        return new Message(Type.warning, message);
    }

    public static Message error(String code, String message) {
        return new Message(Type.error, code, message);
    }

    public static Message info(String code, String message) {
        return new Message(Type.info, code, message);
    }

    public static Message warning(String code, String message) {
        return new Message(Type.warning, code, message);
    }

    public Type getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int compareTo(Message o) {
        return this.type.compareTo(o.getType());
    }

    @Override
    public String toString() {
        return type.getDescription() + ": " + message;
    }

    public String getCode() {
        return code;
    }
}

