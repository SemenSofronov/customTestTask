package com.company.testtask.web.contract;

import com.company.testtask.entity.*;
import com.haulmont.bpm.gui.procactions.ProcActionsFrame;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.cuba.core.sys.AppContext;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.reports.gui.actions.TablePrintFormAction;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ContractEdit extends AbstractEditor<Contract> {

    protected static final String PROCESS_CODE = "contractExecution";

    @Named("rightFieldGroup.vat")
    protected TextField vatField;

    @Named("leftFieldGroup.performer")
    protected LookupPickerField performerField;

    @Named("rightFieldGroup.amount")
    protected TextField amountField;

    @Named("leftFieldGroup.customer")
    protected LookupPickerField customerField;
    @Inject
    protected Metadata metadata;

    @Inject
    protected DataManager dataManager;

    @Inject
    protected ProcActionsFrame procActionsFrame;

    @Inject
    protected CollectionDatasource<Stage, UUID> stageDs;

    @Inject
    protected Datasource<Contract> contractDs;

    @Inject
    protected TimeSource timeSource;

    @Inject
    protected Button buttonInvoice;

    @Inject
    protected Button buttonCertificate;

    @Inject
    protected Table<Stage> stageTable;

    @Inject
    protected CollectionDatasource<Invoice, UUID> invoicesDs;

    @Inject
    protected CollectionDatasource<ServiceCompletionCertificate, UUID> certificateDs;

    @Inject
    protected Table<Invoice> invoicesTable;

    @Inject
    protected Button reportInvoiceButton;

    @Inject
    protected Table<ServiceCompletionCertificate> certificatesTable;

    @Inject
    protected Button reportCertificateButton;

    @Override
    protected void initNewItem(Contract item) {
        super.initNewItem(item);

        String vat = AppContext.getProperty("app.vatValue");
        item.setVat(vat);

        item.setStatus("New");
    }

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        TablePrintFormAction reportInvoiceAction = new TablePrintFormAction("reportInvoice", invoicesTable);
        invoicesTable.addAction(reportInvoiceAction);
        reportInvoiceButton.setAction(reportInvoiceAction);

        TablePrintFormAction reportCertificateAction = new TablePrintFormAction("reportCertificate", certificatesTable);
        certificatesTable.addAction(reportCertificateAction);
        reportCertificateButton.setAction(reportCertificateAction);

        Action createInvoice = new ItemTrackingAction(stageTable, "createInvoice")
                .withHandler(e -> {
                    Set<Stage> selected = stageTable.getSelected();

                    if (selected.size() == 1) {
                        Stage stage = (Stage) selected.toArray()[0];

                        Invoice invoice = metadata.create(Invoice.class);
                        invoice.setAmount(stage.getAmount());
                        invoice.setDate(timeSource.currentTimestamp());
                        invoice.setVat(stage.getVat());

                        invoice.setTotalAmount(stage.getTotalAmount());
                        invoice.setNumber(stage.getContract().getNumber());
                        invoice.setDescription(stage.getDescription());
                        invoice.setStage(stage);

                        Invoice committedInvoice = dataManager.commit(invoice);
                        stage.setInvoice(committedInvoice);
                        dataManager.commit(stage);

                        invoicesDs.refresh();
                        contractDs.refresh();
                    }

                });

        ((BaseAction) createInvoice).addEnabledRule(() -> {
            Set<Stage> selected = stageTable.getSelected();

            if (selected.size() == 1) {
                Stage stage = (Stage) selected.toArray()[0];

                return stage.getInvoice() == null;
            }
            return false;
        });

        stageTable.addAction(createInvoice);

        Action createCertificate = new ItemTrackingAction(stageTable, "createCertificate")
                .withHandler(e -> {
                    Set<Stage> selected = stageTable.getSelected();

                    if (selected.size() == 1) {
                        Stage stage = (Stage) selected.toArray()[0];


                        ServiceCompletionCertificate certificate = metadata.create(ServiceCompletionCertificate.class);
                        certificate.setAmount(stage.getAmount());
                        certificate.setDate(timeSource.currentTimestamp());
                        certificate.setVat(stage.getVat());
                        certificate.setTotalAmount(stage.getTotalAmount());
                        certificate.setNumber(stage.getContract().getNumber());
                        certificate.setDescription(stage.getDescription());
                        certificate.setStage(stage);

                        ServiceCompletionCertificate committedCertificate = dataManager.commit(certificate);
                        stage.setServiceCompletionCertificate(committedCertificate);
                        dataManager.commit(stage);

                        certificateDs.refresh();
                        contractDs.refresh();
                    }

                });

        ((BaseAction) createCertificate).addEnabledRule(() -> {
            Set<Stage> selected = stageTable.getSelected();

            if (selected.size() == 1) {
                Stage stage = (Stage) selected.toArray()[0];

                return stage.getServiceCompletionCertificate() == null;
            }
            return false;
        });

        stageTable.addAction(createCertificate);
    }

    @Override
    protected void postInit() {
        super.postInit();

        ValueChangeListener organizationChangeListener = e -> {
            Contract contract = getItem();

            if (isEscapingVat(contract)) {
                contract.setVat("0%");
            } else {
                contract.setVat(AppContext.getProperty("app.vatValue"));
            }
        };

        performerField.addValueChangeListener(organizationChangeListener);

        customerField.addValueChangeListener(organizationChangeListener);

        amountField.addValueChangeListener(e -> {
            BigDecimal amount = (BigDecimal) e.getValue();

            if (amount != null) {

                Contract contract = getItem();
                String vat = contract.getVat();
                vat = vat.replace("%", "");

                BigDecimal totalAmount = amount.multiply(new BigDecimal(1 + Double.valueOf(vat) / 100));
                contract.setTotalAmount(totalAmount);
            }
        });

        vatField.addValueChangeListener(e -> {
            String vat = (String) e.getValue();

            if (vat != null) {

                Contract contract = getItem();
                BigDecimal amount = contract.getAmount();

                if (amount != null) {

                    vat = vat.replace("%", "");

                    BigDecimal totalAmount = amount.multiply(new BigDecimal(1 + Double.valueOf(vat) / 100));
                    contract.setTotalAmount(totalAmount);
                }
            }
        });
    }

    protected boolean isEscapingVat(Contract contract) {
        Organization customer = contract.getCustomer();
        Organization performer = contract.getPerformer();

        return customer != null && customer.getEscapeVat() || performer != null && performer.getEscapeVat();
    }

    @Override
    protected boolean postCommit(boolean committed, boolean close) {
        if (committed) {
            Contract contract = getItem();

            List<Stage> stages = contract.getStages();
            if (stages.isEmpty()) {

                Stage stage = metadata.create(Stage.class);
                stage.setName("Common");
                stage.setAmount(contract.getAmount());
                stage.setDateFrom(contract.getDateFrom());
                stage.setDateTo(contract.getDateTo());
                stage.setVat(contract.getVat());
                stage.setTotalAmount(contract.getTotalAmount());
                stage.setContract(contract);

                dataManager.commit(stage);
            }
        }

        return super.postCommit(committed, close);
    }

    @Override
    public void ready() {
        procActionsFrame.initializer()
                .standard()
                .init(PROCESS_CODE, getItem());
    }

}