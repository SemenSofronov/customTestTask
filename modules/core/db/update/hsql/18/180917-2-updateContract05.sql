alter table TESTTASK_CONTRACT add constraint FK_TESTTASK_CONTRACT_ON_CUSTOMER foreign key (CUSTOMER_ID) references TESTTASK_ORGANIZATION(ID);
create index IDX_TESTTASK_CONTRACT_ON_CUSTOMER on TESTTASK_CONTRACT (CUSTOMER_ID);
