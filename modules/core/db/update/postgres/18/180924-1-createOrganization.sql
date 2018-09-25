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
);
