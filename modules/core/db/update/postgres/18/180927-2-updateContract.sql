alter table TESTTASK_CONTRACT rename column file_id to file_id__u44654 ;
drop index IDX_TESTTASK_CONTRACT_ON_FILE ;
alter table TESTTASK_CONTRACT drop constraint FK_TESTTASK_CONTRACT_ON_FILE ;
