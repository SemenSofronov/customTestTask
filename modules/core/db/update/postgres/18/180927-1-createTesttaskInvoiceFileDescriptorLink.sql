create table TESTTASK_INVOICE_FILE_DESCRIPTOR_LINK (
    INVOICE_ID uuid,
    FILE_DESCRIPTOR_ID uuid,
    primary key (INVOICE_ID, FILE_DESCRIPTOR_ID)
);
