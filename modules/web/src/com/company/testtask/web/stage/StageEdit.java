package com.company.testtask.web.stage;

import com.company.testtask.configuration.ContractConfig;
import com.company.testtask.entity.Contract;
import com.company.testtask.entity.Stage;
import com.haulmont.cuba.core.global.Configuration;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

public class StageEdit extends AbstractEditor<Stage> {

    @Named("fieldGroup.amount")
    protected TextField amountField;

    @Inject
    protected Configuration configuration;

    @Override
    protected void postInit() {
        super.postInit();

        amountField.addValueChangeListener(e -> {
            Stage stage = getItem();
            BigDecimal amount = stage.getAmount();

            if (amount != null) {
                Contract contract = stage.getContract();
                BigDecimal vat = contract.getVat();
                if (vat == null || vat.intValue() != 0) {
                    setVatFromConfig(stage);
                    vat = stage.getVat();
                } else {
                    stage.setVat(vat);
                }

                BigDecimal totalAmount = amount.add(vat);
                stage.setTotalAmount(totalAmount);
            }
        });

    }

    protected void setVatFromConfig(Stage item) {
        BigDecimal vat = configuration.getConfig(ContractConfig.class).getVat();
        BigDecimal amount = item.getAmount();

        if (vat != null && amount != null) {
            item.setVat(amount.multiply(new BigDecimal(vat.doubleValue() / 100)));
        }
    }
}