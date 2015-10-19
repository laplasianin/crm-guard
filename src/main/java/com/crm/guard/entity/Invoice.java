package com.crm.guard.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "INVOICE")
public class Invoice implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID", nullable = false)
    private Contract contract;

    @Column(name = "NUMBER")
    private String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "INVOICE_DATE")
    private Date invoiceDate;

    @Column(name = "DEBT", precision=10, scale=2)
    private BigDecimal debt;

    @Column(name = "DESCRIPTION")
    @Type(type="text")
    private String description;

    @Column(name = "PERIOD")
    @Type(type="text")
    private String period;

/*    @OneToMany (mappedBy = "invoice", fetch = FetchType.LAZY,  cascade={CascadeType.PERSIST, CascadeType.REMOVE})
    @Cascade( {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    Set<Bill> bills = new HashSet<Bill>();*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getDebt() {
        return debt;
    }

    public void setDebt(BigDecimal debt) {
        this.debt = debt;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }


}
