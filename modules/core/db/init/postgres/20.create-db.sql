-- begin TESTTASK_CONTRACT
alter table TESTTASK_CONTRACT add constraint FK_TESTTASK_CONTRACT_ON_CUSTOMER foreign key (CUSTOMER_ID) references TESTTASK_ORGANIZATION(ID)^
alter table TESTTASK_CONTRACT add constraint FK_TESTTASK_CONTRACT_ON_PERFORMER foreign key (PERFORMER_ID) references TESTTASK_ORGANIZATION(ID)^
alter table TESTTASK_CONTRACT add constraint FK_TESTTASK_CONTRACT_ON_STATUS foreign key (STATUS_ID) references TESTTASK_STATUS(ID)^
create index IDX_TESTTASK_CONTRACT_ON_CUSTOMER on TESTTASK_CONTRACT (CUSTOMER_ID)^
create index IDX_TESTTASK_CONTRACT_ON_PERFORMER on TESTTASK_CONTRACT (PERFORMER_ID)^
create index IDX_TESTTASK_CONTRACT_ON_STATUS on TESTTASK_CONTRACT (STATUS_ID)^
-- end TESTTASK_CONTRACT
-- begin TESTTASK_STAGE
alter table TESTTASK_STAGE add constraint FK_TESTTASK_STAGE_ON_CONTRACT foreign key (CONTRACT_ID) references TESTTASK_CONTRACT(ID)^
create index IDX_TESTTASK_STAGE_ON_CONTRACT on TESTTASK_STAGE (CONTRACT_ID)^
-- end TESTTASK_STAGE
-- begin TESTTASK_SERVICE_COMPLETION_CERTIFICATE
alter table TESTTASK_SERVICE_COMPLETION_CERTIFICATE add constraint FK_TESTTASK_SERVICE_COMPLETION_CERTIFICATE_ON_STAGE foreign key (STAGE_ID) references TESTTASK_STAGE(ID)^
create index IDX_TESTTASK_SERVICE_COMPLETION_CERTIFICATE_ON_STAGE on TESTTASK_SERVICE_COMPLETION_CERTIFICATE (STAGE_ID)^
-- end TESTTASK_SERVICE_COMPLETION_CERTIFICATE
-- begin TESTTASK_INVOICE
alter table TESTTASK_INVOICE add constraint FK_TESTTASK_INVOICE_ON_STAGE foreign key (STAGE_ID) references TESTTASK_STAGE(ID)^
create index IDX_TESTTASK_INVOICE_ON_STAGE on TESTTASK_INVOICE (STAGE_ID)^
-- end TESTTASK_INVOICE
-- begin TESTTASK_INVOICE_FILE_DESCRIPTOR_LINK
alter table TESTTASK_INVOICE_FILE_DESCRIPTOR_LINK add constraint FK_INVFILDES_ON_INVOICE foreign key (INVOICE_ID) references TESTTASK_INVOICE(ID)^
alter table TESTTASK_INVOICE_FILE_DESCRIPTOR_LINK add constraint FK_INVFILDES_ON_FILE_DESCRIPTOR foreign key (FILE_DESCRIPTOR_ID) references SYS_FILE(ID)^
-- end TESTTASK_INVOICE_FILE_DESCRIPTOR_LINK
-- begin TESTTASK_CONTRACT_FILE_DESCRIPTOR_LINK
alter table TESTTASK_CONTRACT_FILE_DESCRIPTOR_LINK add constraint FK_CONFILDES_ON_CONTRACT foreign key (CONTRACT_ID) references TESTTASK_CONTRACT(ID)^
alter table TESTTASK_CONTRACT_FILE_DESCRIPTOR_LINK add constraint FK_CONFILDES_ON_FILE_DESCRIPTOR foreign key (FILE_DESCRIPTOR_ID) references SYS_FILE(ID)^
-- end TESTTASK_CONTRACT_FILE_DESCRIPTOR_LINK
-- begin TESTTASK_SERVICE_COMPLETION_CERTIFICATE_FILE_DESCRIPTOR_LINK
alter table TESTTASK_SERVICE_COMPLETION_CERTIFICATE_FILE_DESCRIPTOR_LINK add constraint FK_SERCOMCERFILDES_ON_SERVICE_COMPLETION_CERTIFICATE foreign key (SERVICE_COMPLETION_CERTIFICATE_ID) references TESTTASK_SERVICE_COMPLETION_CERTIFICATE(ID)^
alter table TESTTASK_SERVICE_COMPLETION_CERTIFICATE_FILE_DESCRIPTOR_LINK add constraint FK_SERCOMCERFILDES_ON_FILE_DESCRIPTOR foreign key (FILE_DESCRIPTOR_ID) references SYS_FILE(ID)^
-- end TESTTASK_SERVICE_COMPLETION_CERTIFICATE_FILE_DESCRIPTOR_LINK
-- begin TESTTASK_STATUS
create unique index IDX_TESTTASK_STATUS_UK_CODE on TESTTASK_STATUS (CODE) where DELETE_TS is null ^
-- end TESTTASK_STATUS
