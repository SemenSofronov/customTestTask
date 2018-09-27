alter table TESTTASK_INVOICE rename column file_id to file_id__u55866 ;
drop index IDX_TESTTASK_INVOICE_ON_FILE ;
alter table TESTTASK_INVOICE drop constraint FK_TESTTASK_INVOICE_ON_FILE ;
