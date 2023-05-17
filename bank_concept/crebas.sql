/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 12                       */
/* Created on:     2023/5/17 22:42:48                           */
/*==============================================================*/


if exists(select 1 from sys.sysforeignkey where role='FK_ACCOUNT_OPEN_SUB_BANK') then
    alter table Account
       delete foreign key FK_ACCOUNT_OPEN_SUB_BANK
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_ACCOUNT_OWN_CLIENT') then
    alter table Account
       delete foreign key FK_ACCOUNT_OWN_CLIENT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_CREDIT_A_CREDIT_ACCOUNT') then
    alter table Credit_account
       delete foreign key FK_CREDIT_A_CREDIT_ACCOUNT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_MANAGER_PROMOTE_MEMBER') then
    alter table Manager
       delete foreign key FK_MANAGER_PROMOTE_MEMBER
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_MEMBER_EMPLOY_SUB_BANK') then
    alter table Member
       delete foreign key FK_MEMBER_EMPLOY_SUB_BANK
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_PAY_STAT_PAY_LOAN_LOAN') then
    alter table Pay_status
       delete foreign key FK_PAY_STAT_PAY_LOAN_LOAN
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SAVING_A_SAVE_ACCOUNT') then
    alter table Saving_account
       delete foreign key FK_SAVING_A_SAVE_ACCOUNT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SERVE_SERVE_MEMBER') then
    alter table Serve
       delete foreign key FK_SERVE_SERVE_MEMBER
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_SERVE_SERVE2_CLIENT') then
    alter table Serve
       delete foreign key FK_SERVE_SERVE2_CLIENT
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_LOAN_LEND_BANK_SUB_BANK') then
    alter table loan
       delete foreign key FK_LOAN_LEND_BANK_SUB_BANK
end if;

if exists(select 1 from sys.sysforeignkey where role='FK_LOAN_LEND_CLIE_CLIENT') then
    alter table loan
       delete foreign key FK_LOAN_LEND_CLIE_CLIENT
end if;

drop index if exists Account.own_FK;

drop index if exists Account.open_FK;

drop index if exists Account.Account_PK;

drop table if exists Account;

drop index if exists Client.Client_PK;

drop table if exists Client;

drop index if exists Credit_account.Credit_account_PK;

drop table if exists Credit_account;

drop index if exists Manager.Manager_PK;

drop table if exists Manager;

drop index if exists Member.employ_FK;

drop index if exists Member.Member_PK;

drop table if exists Member;

drop index if exists Pay_status.Pay_loan_FK;

drop index if exists Pay_status.Pay_status_PK;

drop table if exists Pay_status;

drop index if exists Saving_account.Saving_account_PK;

drop table if exists Saving_account;

drop index if exists Serve.Serve2_FK;

drop index if exists Serve.Serve_FK;

drop index if exists Serve.Serve_PK;

drop table if exists Serve;

drop index if exists loan.Lend_client_FK;

drop index if exists loan.Lend_bank_FK;

drop index if exists loan.loan_PK;

drop table if exists loan;

drop index if exists sub_bank.sub_bank_PK;

drop table if exists sub_bank;

/*==============================================================*/
/* Table: Account                                               */
/*==============================================================*/
create table Account 
(
   Accout_ID            integer                        not null,
   Client_ID            integer                        not null,
   bank_name            char(30)                       not null,
   Password             char(20)                       null,
   Remaining            integer                        null,
   Open_date            date                           null,
   constraint PK_ACCOUNT primary key (Accout_ID)
);

/*==============================================================*/
/* Index: Account_PK                                            */
/*==============================================================*/
create unique index Account_PK on Account (
Accout_ID ASC
);

/*==============================================================*/
/* Index: open_FK                                               */
/*==============================================================*/
create index open_FK on Account (
bank_name ASC
);

/*==============================================================*/
/* Index: own_FK                                                */
/*==============================================================*/
create index own_FK on Account (
Client_ID ASC
);

/*==============================================================*/
/* Table: Client                                                */
/*==============================================================*/
create table Client 
(
   Client_ID            integer                        not null,
   Real_name            char(30)                       null,
   Virtual_name         char(30)                       null,
   Client_password      char(30)                       null,
   Client_phone         integer                        null,
   Client_address       char(30)                       null,
   Client_email         char(30)                       null,
   constraint PK_CLIENT primary key (Client_ID)
);

/*==============================================================*/
/* Index: Client_PK                                             */
/*==============================================================*/
create unique index Client_PK on Client (
Client_ID ASC
);

/*==============================================================*/
/* Table: Credit_account                                        */
/*==============================================================*/
create table Credit_account 
(
   Accout_ID            integer                        not null,
   Client_ID            integer                        null,
   bank_name            char(30)                       null,
   Password             char(20)                       null,
   Remaining            integer                        null,
   Open_date            date                           null,
   overdraft            integer                        null,
   constraint PK_CREDIT_ACCOUNT primary key clustered (Accout_ID)
);

/*==============================================================*/
/* Index: Credit_account_PK                                     */
/*==============================================================*/
create unique clustered index Credit_account_PK on Credit_account (
Accout_ID ASC
);

/*==============================================================*/
/* Table: Manager                                               */
/*==============================================================*/
create table Manager 
(
   ID                   integer                        not null,
   depart_no            integer                        not null,
   Dep_depart_no        integer                        null,
   bank_name            char(30)                       null,
   name                 char(30)                       null,
   sex                  char(1)                        null,
   Person_ID            integer                        null,
   Phone                integer                        null,
   Address              char(30)                       null,
   Salary               integer                        null,
   Begin_date           date                           null,
   level                integer                        null,
   constraint PK_MANAGER primary key clustered (ID)
);

/*==============================================================*/
/* Index: Manager_PK                                            */
/*==============================================================*/
create unique clustered index Manager_PK on Manager (
ID ASC
);

/*==============================================================*/
/* Table: Member                                                */
/*==============================================================*/
create table Member 
(
   ID                   integer                        not null,
   depart_no            integer                        not null,
   bank_name            char(30)                       not null,
   name                 char(30)                       null,
   sex                  char(1)                        null,
   Person_ID            integer                        null,
   Phone                integer                        null,
   Address              char(30)                       null,
   Salary               integer                        null,
   Begin_date           date                           null,
   level                integer                        null,
   constraint PK_MEMBER primary key (ID)
);

/*==============================================================*/
/* Index: Member_PK                                             */
/*==============================================================*/
create unique index Member_PK on Member (
ID ASC
);

/*==============================================================*/
/* Index: employ_FK                                             */
/*==============================================================*/
create index employ_FK on Member (
bank_name ASC
);

/*==============================================================*/
/* Table: Pay_status                                            */
/*==============================================================*/
create table Pay_status 
(
   Pay_ID               char(30)                       not null,
   Loan_ID              integer                        not null,
   Pay_money            integer                        null,
   Pay_date             date                           null,
   constraint PK_PAY_STATUS primary key (Pay_ID)
);

/*==============================================================*/
/* Index: Pay_status_PK                                         */
/*==============================================================*/
create unique index Pay_status_PK on Pay_status (
Pay_ID ASC
);

/*==============================================================*/
/* Index: Pay_loan_FK                                           */
/*==============================================================*/
create index Pay_loan_FK on Pay_status (
Loan_ID ASC
);

/*==============================================================*/
/* Table: Saving_account                                        */
/*==============================================================*/
create table Saving_account 
(
   Accout_ID            integer                        not null,
   Client_ID            integer                        null,
   bank_name            char(30)                       null,
   Password             char(20)                       null,
   Remaining            integer                        null,
   Open_date            date                           null,
   interest_rate        float                          null,
   constraint PK_SAVING_ACCOUNT primary key clustered (Accout_ID)
);

/*==============================================================*/
/* Index: Saving_account_PK                                     */
/*==============================================================*/
create unique clustered index Saving_account_PK on Saving_account (
Accout_ID ASC
);

/*==============================================================*/
/* Table: Serve                                                 */
/*==============================================================*/
create table Serve 
(
   ID                   integer                        not null,
   Client_ID            integer                        not null,
   constraint PK_SERVE primary key clustered (ID, Client_ID)
);

/*==============================================================*/
/* Index: Serve_PK                                              */
/*==============================================================*/
create unique clustered index Serve_PK on Serve (
ID ASC,
Client_ID ASC
);

/*==============================================================*/
/* Index: Serve_FK                                              */
/*==============================================================*/
create index Serve_FK on Serve (
ID ASC
);

/*==============================================================*/
/* Index: Serve2_FK                                             */
/*==============================================================*/
create index Serve2_FK on Serve (
Client_ID ASC
);

/*==============================================================*/
/* Table: loan                                                  */
/*==============================================================*/
create table loan 
(
   Loan_ID              integer                        not null,
   Client_ID            integer                        not null,
   bank_name            char(30)                       not null,
   Loan_total           integer                        null,
   Loan_datw            date                           null,
   Loan_rate            float                          null,
   constraint PK_LOAN primary key (Loan_ID)
);

/*==============================================================*/
/* Index: loan_PK                                               */
/*==============================================================*/
create unique index loan_PK on loan (
Loan_ID ASC
);

/*==============================================================*/
/* Index: Lend_bank_FK                                          */
/*==============================================================*/
create index Lend_bank_FK on loan (
bank_name ASC
);

/*==============================================================*/
/* Index: Lend_client_FK                                        */
/*==============================================================*/
create index Lend_client_FK on loan (
Client_ID ASC
);

/*==============================================================*/
/* Table: sub_bank                                              */
/*==============================================================*/
create table sub_bank 
(
   bank_name            char(30)                       not null,
   bank_location        char(30)                       null,
   asset                integer                        null,
   constraint PK_SUB_BANK primary key (bank_name)
);

/*==============================================================*/
/* Index: sub_bank_PK                                           */
/*==============================================================*/
create unique index sub_bank_PK on sub_bank (
bank_name ASC
);

alter table Account
   add constraint FK_ACCOUNT_OPEN_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table Account
   add constraint FK_ACCOUNT_OWN_CLIENT foreign key (Client_ID)
      references Client (Client_ID)
      on update restrict
      on delete restrict;

alter table Credit_account
   add constraint FK_CREDIT_A_CREDIT_ACCOUNT foreign key (Accout_ID)
      references Account (Accout_ID)
      on update restrict
      on delete restrict;

alter table Manager
   add constraint FK_MANAGER_PROMOTE_MEMBER foreign key (ID)
      references Member (ID)
      on update restrict
      on delete restrict;

alter table Member
   add constraint FK_MEMBER_EMPLOY_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table Pay_status
   add constraint FK_PAY_STAT_PAY_LOAN_LOAN foreign key (Loan_ID)
      references loan (Loan_ID)
      on update restrict
      on delete restrict;

alter table Saving_account
   add constraint FK_SAVING_A_SAVE_ACCOUNT foreign key (Accout_ID)
      references Account (Accout_ID)
      on update restrict
      on delete restrict;

alter table Serve
   add constraint FK_SERVE_SERVE_MEMBER foreign key (ID)
      references Member (ID)
      on update restrict
      on delete restrict;

alter table Serve
   add constraint FK_SERVE_SERVE2_CLIENT foreign key (Client_ID)
      references Client (Client_ID)
      on update restrict
      on delete restrict;

alter table loan
   add constraint FK_LOAN_LEND_BANK_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table loan
   add constraint FK_LOAN_LEND_CLIE_CLIENT foreign key (Client_ID)
      references Client (Client_ID)
      on update restrict
      on delete restrict;

