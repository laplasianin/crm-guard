package com.crm.guard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;

@javax.persistence.Entity
@Table(name = "TOTAL_INVOICE")
public class TotalInvoiceSimple implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @JsonIgnore
    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "INVOICE", precision=10, scale=2)
    private BigDecimal invoice;

    public TotalInvoiceSimple() {
    }

    public TotalInvoiceSimple(String clientId, BigDecimal invoice) {
        this.clientId = clientId;
        this.invoice = invoice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getInvoice() {
        return invoice;
    }

    public void setInvoice(BigDecimal invoice) {
        this.invoice = invoice;
    }
}