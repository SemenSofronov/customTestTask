update TESTTASK_ORGANIZATION set ESCAPE_VAT = false where ESCAPE_VAT is null ;
alter table TESTTASK_ORGANIZATION alter column ESCAPE_VAT set not null ;
