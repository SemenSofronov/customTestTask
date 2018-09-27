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
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import com.haulmont.cuba.core.entity.FileDescriptor;
import java.util.List;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@NamePattern("%s|description")
@Table(name = "TESTTASK_SERVICE_COMPLETION_CERTIFICATE")
@Entity(name = "testtask$ServiceCompletionCertificate")
public class ServiceCompletionCertificate extends StandardEntity {
    private static final long serialVersionUID = -6845817761781025245L;

    @Column(name = "NUMBER_")
    protected String number;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_")
    protected Date date;

    @Column(name = "AMOUNT")
    protected BigDecimal amount;

    @Column(name = "VAT")
    protected BigDecimal vat;

    @Column(name = "TOTAL_AMOUNT")
    protected BigDecimal totalAmount;

    @Column(name = "DESCRIPTION")
    protected String description;

    @JoinColumn(name = "STAGE_ID")
    @OneToOne(fetch = FetchType.LAZY)
    protected Stage stage;

    @JoinTable(name = "TESTTASK_SERVICE_COMPLETION_CERTIFICATE_FILE_DESCRIPTOR_LINK",
        joinColumns = @JoinColumn(name = "SERVICE_COMPLETION_CERTIFICATE_ID"),
        inverseJoinColumns = @JoinColumn(name = "FILE_DESCRIPTOR_ID"))
    @ManyToMany
    protected List<FileDescriptor> file;

    public List<FileDescriptor> getFile() {
        return file;
    }

    public void setFile(List<FileDescriptor> file) {
        this.file = file;
    }



    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
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