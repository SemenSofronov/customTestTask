package com.company.testtask.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "TESTTASK_STATUS")
@Entity(name = "testtask$Status")
public class Status extends StandardEntity {
    private static final long serialVersionUID = 3964415633397368253L;

    @Column(name = "CODE", unique = true)
    protected Long code;

    @Column(name = "NAME")
    protected String name;

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}