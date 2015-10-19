package com.crm.guard.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "INTEGRATION_AUDIT")
public class IntegrationAudit implements AbstractEntity {

    public void addToLog(String s) {
        log += "\r\n" + s;
    }

    public enum Type { full, clients, contracts, total_invoice, invoices}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private Type type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NAME")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", columnDefinition="DATETIME")
    private Date  created = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "COMPLETED", columnDefinition="DATETIME")
    private Date  completed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ERROR_ENDED", columnDefinition="DATETIME")
    private Date  terminated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VERSION", columnDefinition="DATETIME")
    private Date  version = new Date();

    @Column(name = "LOG")
    @org.hibernate.annotations.Type(type = "text")
    private String log = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCompleted() {
        return completed;
    }

    public void setCompleted(Date completed) {
        this.completed = completed;
    }

    public Date getTerminated() {
        return terminated;
    }

    public void setTerminated(Date terminated) {
        this.terminated = terminated;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }
}