package com.crm.guard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "CLIENT_GROUP")
public class ClientGroup implements AbstractEntity {

    @Id
    @Column(name="GROUP_CODE", nullable = false, unique = true)
    private String code;

    @Column(name="DESCRIPTION")
    private String description;

    @JsonIgnore
    @XmlTransient
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "CLIENT_HAS_GROUP", joinColumns = {
            @JoinColumn(name = "GROUP_CODE", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "CLIENT_ID", nullable = false, updatable = false) })
    private Set<Client> clients = new HashSet<Client>(0);

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
}
