-- begin TESTTASK_ORGANIZATION
create table TESTTASK_ORGANIZATION (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    TAX_NUMBER varchar(255),
    REGISTRATION_NUMBER varchar(255),
    ESCAPE_VAT boolean not null,
    --
    primary key (ID)
)^
-- end TESTTASK_ORGANIZATION
-- begin TESTTASK_CONTRACT
create table TESTTASK_CONTRACT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CUSTOMER_ID uuid not null,
    PERFORMER_ID uuid not null,
    NUMBER_ varchar(255),
    SIGNED_DATE date,
    TYPE_ integer,
    DATE_FROM date,
    DATE_TO date,
    AMOUNT decimal(19, 2),
    VAT decimal(19, 2),
    TOTAL_AMOUNT decimal(19, 2),
    CUSTOMER_SIGNER varchar(255),
    PERFORMER_SIGNER varchar(255),
    STATUS_ID uuid,
    --
    primary key (ID)
)^
-- end TESTTASK_CONTRACT
-- begin TESTTASK_STAGE
create table TESTTASK_STAGE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255),
    DATE_FROM date,
    DATE_TO date,
    AMOUNT decimal(19, 2),
    VAT decimal(19, 2),
    TOTAL_AMOUNT decimal(19, 2),
    DESCRIPTION varchar(255),
    CONTRACT_ID uuid,
    --
    primary key (ID)
)^
-- end TESTTASK_STAGE
-- begin TESTTASK_SERVICE_COMPLETION_CERTIFICATE
create table TESTTASK_SERVICE_COMPLETION_CERTIFICATE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255),
    DATE_ date,
    AMOUNT decimal(19, 2),
    VAT decimal(19, 2),
    TOTAL_AMOUNT decimal(19, 2),
    DESCRIPTION varchar(255),
    STAGE_ID uuid,
    --
    primary key (ID)
)^
-- end TESTTASK_SERVICE_COMPLETION_CERTIFICATE
-- begin TESTTASK_INVOICE
create table TESTTASK_INVOICE (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255),
    DATE_ date,
    AMOUNT decimal(19, 2),
    VAT decimal(19, 2),
    TOTAL_AMOUNT decimal(19, 2),
    DESCRIPTION varchar(255),
    STAGE_ID uuid,
    --
    primary key (ID)
)^
-- end TESTTASK_INVOICE
-- begin TESTTASK_STATUS
create table TESTTASK_STATUS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CODE bigint,
    NAME varchar(255),
    --
    primary key (ID)
)^
-- end TESTTASK_STATUS
-- begin TESTTASK_INVOICE_FILE_DESCRIPTOR_LINK
create table TESTTASK_INVOICE_FILE_DESCRIPTOR_LINK (
    INVOICE_ID uuid,
    FILE_DESCRIPTOR_ID uuid,
    primary key (INVOICE_ID, FILE_DESCRIPTOR_ID)
)^
-- end TESTTASK_INVOICE_FILE_DESCRIPTOR_LINK
-- begin TESTTASK_CONTRACT_FILE_DESCRIPTOR_LINK
create table TESTTASK_CONTRACT_FILE_DESCRIPTOR_LINK (
    CONTRACT_ID uuid,
    FILE_DESCRIPTOR_ID uuid,
    primary key (CONTRACT_ID, FILE_DESCRIPTOR_ID)
)^
-- end TESTTASK_CONTRACT_FILE_DESCRIPTOR_LINK
-- begin TESTTASK_SERVICE_COMPLETION_CERTIFICATE_FILE_DESCRIPTOR_LINK
create table TESTTASK_SERVICE_COMPLETION_CERTIFICATE_FILE_DESCRIPTOR_LINK (
    SERVICE_COMPLETION_CERTIFICATE_ID uuid,
    FILE_DESCRIPTOR_ID uuid,
    primary key (SERVICE_COMPLETION_CERTIFICATE_ID, FILE_DESCRIPTOR_ID)
)^
-- end TESTTASK_SERVICE_COMPLETION_CERTIFICATE_FILE_DESCRIPTOR_LINK
