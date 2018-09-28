package com.company.testtask.web.contract;

import com.company.testtask.configuration.ContractConfig;
import com.company.testtask.entity.*;
import com.haulmont.bpm.entity.ProcActor;
import com.haulmont.bpm.entity.ProcInstance;
import com.haulmont.bpm.gui.procactions.ProcActionsFrame;
import com.haulmont.cuba.core.app.EmailService;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.*;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.DataSupplier;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.reports.gui.actions.TablePrintFormAction;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.*;

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

    @Named("leftFieldGroup.status")
    protected PickerField statusField;

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
    protected EmailService emailService;

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

    @Inject
    protected Configuration configuration;

    @Inject
    protected FileMultiUploadField fileMultiUpload;

    @Inject
    protected DataSupplier dataSupplier;

    @Inject
    protected FileUploadingAPI fileUploadingAPI;

    @Inject
    protected CollectionDatasource<FileDescriptor, UUID> fileDs;

    @Override
    protected void initNewItem(Contract item) {
        super.initNewItem(item);

        Status status = dataManager.load(Status.class)
                .query("select c from testtask$Status c where c.code = :code")
                .parameter("code", 1)
                .one();

        item.setStatus(status);
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
                    Stage stage = stageTable.getSingleSelected();

                    if (stage != null) {

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
            Stage stage = stageTable.getSingleSelected();

            if (stage != null) {
                return stage.getInvoice() == null;
            }

            return false;
        });

        stageTable.addAction(createInvoice);

        Action createCertificate = new ItemTrackingAction(stageTable, "createCertificate")
                .withHandler(e -> {
                    Stage stage = stageTable.getSingleSelected();

                    if (stage != null) {

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
            Stage stage = stageTable.getSingleSelected();

            if (stage != null) {
                return stage.getServiceCompletionCertificate() == null;
            }

            return false;
        });

        stageTable.addAction(createCertificate);

        fileMultiUpload.addQueueUploadCompleteListener(() -> {
            List<FileDescriptor> files = new ArrayList<>();
            for (Map.Entry<UUID, String> entry : fileMultiUpload.getUploadsMap().entrySet()) {
                UUID fileId = entry.getKey();
                String fileName = entry.getValue();
                FileDescriptor fd = fileUploadingAPI.getFileDescriptor(fileId, fileName);
                files.add(fd);
                // save file to FileStorage
                try {
                    fileUploadingAPI.putFileIntoStorage(fileId, fd);
                } catch (FileStorageException e) {
                    throw new RuntimeException("Error saving file to FileStorage", e);
                }
                // save file descriptor to database
                dataSupplier.commit(fd);
            }
            getItem().setFiles(files);
            showNotification("Uploaded files: " + fileMultiUpload.getUploadsMap().values(), NotificationType.HUMANIZED);
            fileMultiUpload.clearUploads();
            fileDs.refresh();
        });

        fileMultiUpload.addFileUploadErrorListener(event ->
                showNotification("File upload error", NotificationType.HUMANIZED));
    }

    @Override
    protected void postInit() {
        super.postInit();

        ValueChangeListener organizationChangeListener = e -> {
            Contract contract = getItem();

            if (isEscapingVat(contract)) {
                contract.setVat(new BigDecimal(0));
            } else {
                setVatFromConfig(contract);
            }
        };

        performerField.addValueChangeListener(organizationChangeListener);

        customerField.addValueChangeListener(organizationChangeListener);

        ValueChangeListener totalAmountChangeListener = e -> {
            Contract contract = getItem();
            BigDecimal amount = contract.getAmount();

            if (amount != null) {
                BigDecimal vat = contract.getVat();
                if (vat == null || !isEscapingVat(contract)) {
                    setVatFromConfig(contract);
                    vat = contract.getVat();
                }

                BigDecimal totalAmount = amount.add(vat);
                contract.setTotalAmount(totalAmount);
            }
        };

        amountField.addValueChangeListener(totalAmountChangeListener);

        vatField.addValueChangeListener(totalAmountChangeListener);
    }

    protected boolean isEscapingVat(Contract contract) {
        Organization customer = contract.getCustomer();
        Organization performer = contract.getPerformer();

        return customer != null && customer.getEscapeVat() || performer != null && performer.getEscapeVat();
    }

    protected void setVatFromConfig(Contract item) {
        BigDecimal vat = configuration.getConfig(ContractConfig.class).getVat();
        BigDecimal amount = item.getAmount();

        if (vat != null && amount != null) {
            item.setVat(amount.multiply(vat.divide(new BigDecimal(100))));
        }
    }

    @Override
    public void ready() {
        procActionsFrame.initializer()
                .standard()
                .init(PROCESS_CODE, getItem());
    }

}