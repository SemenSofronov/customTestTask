package com.company.testtask.web.invoice;

import com.company.testtask.entity.Invoice;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.reports.gui.actions.TablePrintFormAction;

import javax.inject.Inject;
import java.util.Map;

public class InvoiceBrowse extends AbstractLookup {

    @Inject
    protected Button reportButton;

    @Inject
    protected GroupTable<Invoice> invoicesTable;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        TablePrintFormAction action = new TablePrintFormAction("report", invoicesTable);
        invoicesTable.addAction(action);
        reportButton.setAction(action);
    }
}