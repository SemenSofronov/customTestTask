package com.company.testtask;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("testtask_AmountCalculator")
public class AmountCalculator {

    public BigDecimal calculateTotalAmount(BigDecimal amount, BigDecimal vat) {
        return amount.multiply(new BigDecimal(1 + vat.doubleValue() / 100));
    }
}
