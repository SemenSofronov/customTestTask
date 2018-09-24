-- alter table TESTTASK_CONTRACT add column CUSTOMER_ID varchar(36) ^
-- update TESTTASK_CONTRACT set CUSTOMER_ID = <default_value> ;
-- alter table TESTTASK_CONTRACT alter column CUSTOMER_ID set not null ;
alter table TESTTASK_CONTRACT add column CUSTOMER_ID varchar(36) not null ;
-- update TESTTASK_CONTRACT set PERFORMER_ID = <default_value> where PERFORMER_ID is null ;
alter table TESTTASK_CONTRACT alter column PERFORMER_ID set not null ;
