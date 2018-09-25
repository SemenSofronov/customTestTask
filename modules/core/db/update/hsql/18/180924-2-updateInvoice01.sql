alter table TESTTASK_INVOICE alter column NUMBER_ rename to NUMBER___U53613 ^
alter table TESTTASK_INVOICE alter column VAT rename to VAT__U06220 ^
alter table TESTTASK_INVOICE add column VAT decimal(19, 2) ;
alter table TESTTASK_INVOICE add column NUMBER_ varchar(255) ;
