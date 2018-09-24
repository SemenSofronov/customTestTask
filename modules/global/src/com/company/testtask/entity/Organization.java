package com.company.testtask.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.List;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "TESTTASK_ORGANIZATION")
@Entity(name = "testtask$Organization")
public class Organization extends StandardEntity {
    private static final long serialVersionUID = 5613075774035809561L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "TAX_NUMBER")
    protected String taxNumber;

    @Column(name = "REGISTRATION_NUMBER")
    protected String registrationNumber;

    @OneToMany(mappedBy = "performer")
    protected List<Contract> contract;

    @NotNull
    @Column(name = "ESCAPE_VAT", nullable = false)
    protected Boolean escapeVat = false;

    public void setEscapeVat(Boolean escapeVat) {
        this.escapeVat = escapeVat;
    }

    public Boolean getEscapeVat() {
        return escapeVat;
    }


    public void setContract(List<Contract> contract) {
        this.contract = contract;
    }

    public List<Contract> getContract() {
        return contract;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }


}