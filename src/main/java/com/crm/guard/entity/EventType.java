package com.crm.guard.entity;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "EVENT_TYPE")
public class EventType implements AbstractEntity {

    @Id
    @Column(name="CODE", nullable = false, unique = true)
    private String code;

    @Column(name="NAME")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
