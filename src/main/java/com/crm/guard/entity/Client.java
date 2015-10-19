package com.crm.guard.entity;

import com.crm.guard.utils.hibernate.HibernateUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang.StringUtils.isEmpty;

@javax.persistence.Entity
@Table(name = "CLIENT")
public class Client implements AbstractEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @NotNull
    @Column(name = "SHORT_NAME")
    private String shortName;

    @NotNull
    @Column(name = "FULL_NAME")
    private String fullName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private ClientType type;

    @NotNull
    @Column(name = "INN")
    private String inn;

    @Column(name = "OKPO")
    private String okpo;

    @Column(name = "PERSONAL_ACCOUNT")
    private String personalAccount;

    @Column(name = "REGISTRATION_ADDRESS")
    private String registrationAddress;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "DISABLED", columnDefinition = "BIT", length = 1)
    private Boolean disabled;

    // todo так как есть дубликать кодов в 1с, нужно знать их все. Разделитель ;
    // формат код-номерфилиала;код-номерфилиала;код-номерфилиала;
    // разные коды
    @Column(name = "ALL_IDS")
    @Type(type="text")
    private String allIds = "";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPERATOR")
    private User operator;

    @JsonIgnore
    @XmlTransient
    @OneToMany (mappedBy = "client", fetch = FetchType.LAZY,  cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @Cascade( {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    Set<Contact> contacts = new HashSet<Contact>();

    @JsonIgnore
    @XmlTransient
    @OneToMany (mappedBy = "client", fetch = FetchType.LAZY)
    Set<File> files = new HashSet<File>();

    @JsonIgnore
    @XmlTransient
    @OneToMany (mappedBy = "client", fetch = FetchType.LAZY,  cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @Cascade( {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    Set<Contract> contracts = new HashSet<Contract>();

    @JsonIgnore
    @XmlTransient
    @OneToMany (mappedBy = "client", fetch = FetchType.LAZY,  cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @Cascade( {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    Set<ClientToDo> clientToDos = new HashSet<ClientToDo>();

    @JsonIgnore
    @XmlTransient
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(name = "CLIENT_HAS_GROUP", joinColumns = {
            @JoinColumn(name = "CLIENT_ID", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "GROUP_CODE",
                    nullable = false, updatable = false) })
    private Set<ClientGroup> groups = new HashSet<ClientGroup>(0);

    @OneToOne(mappedBy = "client")
    private TotalInvoice totalInvoice;

    @Column(name="email")
    private String email;

    public Set<ClientToDo> getClientToDos() {
        return clientToDos;
    }

    public void setClientToDos(Set<ClientToDo> clientToDos) {
        this.clientToDos = clientToDos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getOkpo() {
        return okpo;
    }

    public void setOkpo(String okpo) {
        this.okpo = okpo;
    }

    public String getPersonalAccount() {
        return personalAccount;
    }

    public void setPersonalAccount(String personalAccount) {
        this.personalAccount = personalAccount;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public TotalInvoice getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(TotalInvoice totalInvoice) {
        this.totalInvoice = totalInvoice;
    }

    public Set<File> getFiles() {
        return files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public String getAllIds() {
        return allIds;
    }

    public void setAllIds(String allIds) {
        this.allIds = allIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        o = HibernateUtils.deproxy(o);

        if (!(o instanceof Client)) {
            return false;
        }

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addCode(String id) {
        if (!StringUtils.containsIgnoreCase(allIds, id)) {
            if (isEmpty(allIds)) {
                allIds = id + ";";
            } else {
                allIds += id + ";";
            }
        }
    }

    public static String buildId(String inn, String kpp) {
        if (StringUtils.isEmpty(kpp)) {
            return inn;
        }
        return inn + "-" + kpp;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
