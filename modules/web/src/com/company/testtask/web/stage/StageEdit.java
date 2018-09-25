package com.company.testtask.web.stage;

import com.company.testtask.AmountCalculator;
import com.company.testtask.entity.Contract;
import com.company.testtask.entity.Stage;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;

public class StageEdit extends AbstractEditor<Stage> {

    @Named("fieldGroup.amount")
    protected TextField amountField;

    @Named("fieldGroup.vat")
    protected TextField vatField;

    @Inject
    protected AmountCalculator amountCalculator;

    @Override
    protected void initNewItem(Stage item) {
        super.initNewItem(item);

        Contract contract = item.getContract();

        item.setVat(contract.getVat());
    }

    @Override
    protected void postInit() {
        super.postInit();

        amountField.addValueChangeListener(e -> {
            BigDecimal amount = (BigDecimal) e.getValue();

            if (amount != null) {

                Stage stage = getItem();
                BigDecimal vat = stage.getVat();

                BigDecimal totalAmount = amountCalculator.calculateTotalAmount(amount, vat);
                stage.setTotalAmount(totalAmount);
            }
        });

        vatField.addValueChangeListener(e -> {
            BigDecimal vat = (BigDecimal) e.getValue();

            if (vat != null) {

                Stage stage = getItem();
                BigDecimal amount = stage.getAmount();

                if (amount != null) {

                    BigDecimal totalAmount = amountCalculator.calculateTotalAmount(amount, vat);
                    stage.setTotalAmount(totalAmount);
                }
            }
        });
    }
}