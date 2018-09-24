package com.company.testtask.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ContractType implements EnumClass<Integer> {

    FixPrice(10),
    TimeAndMaterial(20),
    OutStaff(30);

    private Integer id;

    ContractType(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ContractType fromId(Integer id) {
        for (ContractType at : ContractType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}