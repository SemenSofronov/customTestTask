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
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;

@NamePattern("%s|number")
@Table(name = "TESTTASK_CONTRACT")
@Entity(name = "testtask$Contract")
public class Contract extends StandardEntity {
    private static final long serialVersionUID = 3392155078909384112L;

    @Lookup(type = LookupType.DROPDOWN)
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    protected Organization customer;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "PERFORMER_ID")
    protected Organization performer;

    @Column(name = "NUMBER_")
    protected String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "SIGNED_DATE")
    protected Date signedDate;

    @Column(name = "TYPE_")
    protected Integer type;

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

    @Column(name = "CUSTOMER_SIGNER")
    protected String customerSigner;

    @Column(name = "PERFORMER_SIGNER")
    protected String performerSigner;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "contract")
    protected List<Stage> stages;

    @Column(name = "STATUS")
    protected String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public List<Stage> getStages() {
        return stages;
    }


    public Organization getCustomer() {
        return customer;
    }

    public void setCustomer(Organization customer) {
        this.customer = customer;
    }



    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public void setVat(String vat) {
        this.vat = vat;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


    public void setPerformer(Organization performer) {
        this.performer = performer;
    }

    public Organization getPerformer() {
        return performer;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    public Date getSignedDate() {
        return signedDate;
    }

    public void setType(ContractType type) {
        this.type = type == null ? null : type.getId();
    }

    public ContractType getType() {
        return type == null ? null : ContractType.fromId(type);
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

    public BigDecimal getAmount() {
        return amount;
    }

    public String getVat() {
        return vat;
    }



    public void setCustomerSigner(String customerSigner) {
        this.customerSigner = customerSigner;
    }

    public String getCustomerSigner() {
        return customerSigner;
    }

    public void setPerformerSigner(String performerSigner) {
        this.performerSigner = performerSigner;
    }

    public String getPerformerSigner() {
        return performerSigner;
    }


}