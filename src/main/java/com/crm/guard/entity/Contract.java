package com.crm.guard.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "CONTRACT")
public class Contract implements AbstractEntity {

    @Id
    @Column(name = "ID", unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTITY_ID", nullable = false)
    private Entity entity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;

    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "CONTRACT_START_DATE")
    private Date contractStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "CONTRACT_END_DATE")
    private Date contractEndDate;

    @OneToMany (mappedBy = "contract", fetch = FetchType.LAZY,  cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @Cascade( {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    Set<Invoice> invoices = new HashSet<Invoice>();

    @Column(name = "TOTAL_INVOICE", precision=10, scale=2)
    private BigDecimal totalInvoice;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractDate) {
        this.contractStartDate = contractDate;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (id != null ? !id.equals(contract.id) : contract.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Date getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(Date contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public void setTotalInvoice(BigDecimal totalInvoice) {
        this.totalInvoice = totalInvoice;
    }

    public BigDecimal getTotalInvoice() {
        return totalInvoice;
    }
}
