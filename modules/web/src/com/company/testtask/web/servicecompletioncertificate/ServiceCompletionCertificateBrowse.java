package com.company.testtask.web.servicecompletioncertificate;

import com.company.testtask.entity.ServiceCompletionCertificate;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.reports.gui.actions.TablePrintFormAction;

import javax.inject.Inject;
import java.util.Map;

public class ServiceCompletionCertificateBrowse extends AbstractLookup {

    @Inject
    protected Button reportButton;
    @Inject
    protected GroupTable<ServiceCompletionCertificate> serviceCompletionCertificatesTable;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        TablePrintFormAction action = new TablePrintFormAction("report", serviceCompletionCertificatesTable);
        serviceCompletionCertificatesTable.addAction(action);
        reportButton.setAction(action);
    }
}