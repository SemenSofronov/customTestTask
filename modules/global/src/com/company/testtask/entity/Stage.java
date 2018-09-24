package com.company.testtask.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;

@NamePattern("%s|name")
@Table(name = "TESTTASK_STAGE")
@Entity(name = "testtask$Stage")
public class Stage extends StandardEntity {
    private static final long serialVersionUID = -5507916182962963131L;

    @Column(name = "NAME")
    protected String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FROM")
    protected Date dateFrom;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TO")
    protected Date dateTo;

    @Column(name = "AMOUNT")
    protected BigDecimal amount;

    @Column(name = "VAT")
    protected String vat;

    @Column(name = "TOTAL_AMOUNT")
    protected BigDecimal totalAmount;

    @Column(name = "DESCRIPTION")
    protected String description;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "stage")
    protected ServiceCompletionCertificate serviceCompletionCertificate;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "stage")
    protected Invoice invoice;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    protected Contract contract;

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Contract getContract() {
        return contract;
    }


    public void setServiceCompletionCertificate(ServiceCompletionCertificate serviceCompletionCertificate) {
        this.serviceCompletionCertificate = serviceCompletionCertificate;
    }

    public ServiceCompletionCertificate getServiceCompletionCertificate() {
        return serviceCompletionCertificate;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Invoice getInvoice() {
        return invoice;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getVat() {
        return vat;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


}