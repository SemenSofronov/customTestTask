package com.company.testtask.web.stage;

import com.company.testtask.entity.Contract;
import com.company.testtask.entity.Stage;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.TextField;

import javax.inject.Named;
import java.math.BigDecimal;

public class StageEdit extends AbstractEditor<Stage> {

    @Named("fieldGroup.amount")
    protected TextField amountField;

    @Named("fieldGroup.vat")
    protected TextField vatField;

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
                String vat = stage.getVat();
                vat = vat.replace("%", "");

                BigDecimal totalAmount = amount.multiply(new BigDecimal(1 + Double.valueOf(vat) / 100));
                stage.setTotalAmount(totalAmount);
            }
        });

        vatField.addValueChangeListener(e -> {
            String vat = (String) e.getValue();

            if (vat != null) {

                Stage stage = getItem();
                BigDecimal amount = stage.getAmount();

                if (amount != null) {

                    vat = vat.replace("%", "");

                    BigDecimal totalAmount = amount.multiply(new BigDecimal(1 + Double.valueOf(vat) / 100));
                    stage.setTotalAmount(totalAmount);
                }
            }
        });
    }
}