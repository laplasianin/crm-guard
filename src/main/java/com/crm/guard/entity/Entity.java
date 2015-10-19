package com.crm.guard.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "ENTITY")
public class Entity implements AbstractEntity {

    @Id
    @Column(name = "ID", unique = true)
    private Long id;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}