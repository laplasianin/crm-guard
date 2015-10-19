package com.crm.guard.entity;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "CRM_SETTINGS")
public class Settings implements AbstractEntity {

    @Id
    @Column(name = "SETTING_CODE", unique = true)
    private String id;

    @Column(name = "SETTING_NAME")
    private String name;

    @Column(name = "SETTING_VALUE")
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
