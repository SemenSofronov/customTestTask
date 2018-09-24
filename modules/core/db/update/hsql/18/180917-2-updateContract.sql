update TESTTASK_CONTRACT set VAT = '' where VAT is null ;
alter table TESTTASK_CONTRACT alter column VAT set not null ;
