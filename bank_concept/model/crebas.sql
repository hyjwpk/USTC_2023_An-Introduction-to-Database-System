/*==============================================================*/
/* Table: Client                                                */
/*==============================================================*/
create table Client 
(
   Client_ID            char(18)                       not null,
   Real_name            char(30)                       null,
   Client_phone         char(11)                       null,
   Client_address       char(30)                       null,
   Client_email         char(30)                       null,
   constraint PK_CLIENT primary key (Client_ID)
);

/*==============================================================*/
/* Table: Credit_account                                        */
/*==============================================================*/
create table Credit_account 
(
   Accout_ID            integer                        not null,
   Client_ID            char(18)                       not null,
   bank_name            char(30)                       not null,
   Password             char(20)                       null,
   Remaining            integer                        null,
   Open_date            date                           null,
   overdraft            integer                        null,
   constraint PK_CREDIT_ACCOUNT primary key clustered (Accout_ID)
);

/*==============================================================*/
/* Table: Department                                            */
/*==============================================================*/
create table Department 
(
   depart_no            integer                        not null,
   ID                   integer                        null,
   bank_name            char(30)                       not null,
   depart_name          char(30)                       null,
   depart_type          char(30)                       null,
   constraint PK_DEPARTMENT primary key (depart_no)
);

/*==============================================================*/
/* Table: Member                                                */
/*==============================================================*/
create table Member 
(
   ID                   integer                        not null,
   depart_no            integer                        null,
   Dep_depart_no        integer                        not null,
   bank_name            char(30)                       not null,
   name                 char(30)                       null,
   sex                  char(1)                        null,
   Person_ID            char(18)                       null,
   Phone                char(11)                       null,
   Address              char(30)                       null,
   Salary               integer                        null,
   Begin_date           date                           null,
   level                integer                        null,
   constraint PK_MEMBER primary key (ID)
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
/* Table: Saving_account                                        */
/*==============================================================*/
create table Saving_account 
(
   Accout_ID            integer                        not null,
   Client_ID            char(18)                       not null,
   bank_name            char(30)                       not null,
   Password             char(20)                       null,
   Remaining            integer                        null,
   Open_date            date                           null,
   interest_rate        float                          null,
   constraint PK_SAVING_ACCOUNT primary key clustered (Accout_ID)
);

/*==============================================================*/
/* Table: Serve                                                 */
/*==============================================================*/
create table Serve 
(
   ID                   integer                        not null,
   Client_ID            char(18)                       not null,
   constraint PK_SERVE primary key clustered (ID, Client_ID)
);

/*==============================================================*/
/* Table: credit_unique                                         */
/*==============================================================*/
create table credit_unique 
(
   bank_name            char(30)                       not null,
   Accout_ID            integer                        not null,
   Client_ID            char(18)                       not null,
   constraint PK_CREDIT_UNIQUE primary key clustered (bank_name, Accout_ID, Client_ID)
);

/*==============================================================*/
/* Table: loan                                                  */
/*==============================================================*/
create table loan 
(
   Loan_ID              integer                        not null,
   Client_ID            char(18)                       not null,
   bank_name            char(30)                       not null,
   Loan_total           integer                        null,
   Loan_datw            date                           null,
   Loan_rate            float                          null,
   constraint PK_LOAN primary key (Loan_ID)
);

/*==============================================================*/
/* Table: saving_unique                                         */
/*==============================================================*/
create table saving_unique 
(
   Accout_ID            integer                        not null,
   bank_name            char(30)                       not null,
   Client_ID            char(18)                       not null,
   constraint PK_SAVING_UNIQUE primary key clustered (Accout_ID, bank_name, Client_ID)
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

alter table Credit_account
   add constraint FK_CREDIT_A_OPEN_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table Credit_account
   add constraint FK_CREDIT_A_OWN_CLIENT foreign key (Client_ID)
      references Client (Client_ID)
      on update restrict
      on delete restrict;

alter table Department
   add constraint FK_DEPARTME_INCLUDE_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table Department
   add constraint FK_DEPARTME_MANAGE_MEMBER foreign key (ID)
      references Member (ID)
      on update restrict
      on delete restrict;

alter table Member
   add constraint FK_MEMBER_EMPLOY_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table Member
   add constraint FK_MEMBER_MANAGE2_DEPARTME foreign key (depart_no)
      references Department (depart_no)
      on update restrict
      on delete restrict;

alter table Member
   add constraint FK_MEMBER_WORK_IN_DEPARTME foreign key (Dep_depart_no)
      references Department (depart_no)
      on update restrict
      on delete restrict;

alter table Pay_status
   add constraint FK_PAY_STAT_PAY_LOAN_LOAN foreign key (Loan_ID)
      references loan (Loan_ID)
      on update restrict
      on delete restrict;

alter table Saving_account
   add constraint FK_SAVING_A_OPEN2_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table Saving_account
   add constraint FK_SAVING_A_OWN2_CLIENT foreign key (Client_ID)
      references Client (Client_ID)
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

alter table credit_unique
   add constraint FK_CREDIT_U_CREDIT_UN_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table credit_unique
   add constraint FK_CREDIT_U_CREDIT_UN_CREDIT_A foreign key (Accout_ID)
      references Credit_account (Accout_ID)
      on update restrict
      on delete restrict;

alter table credit_unique
   add constraint FK_CREDIT_U_CREDIT_UN_CLIENT foreign key (Client_ID)
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

alter table saving_unique
   add constraint FK_SAVING_U_SAVING_UN_SAVING_A foreign key (Accout_ID)
      references Saving_account (Accout_ID)
      on update restrict
      on delete restrict;

alter table saving_unique
   add constraint FK_SAVING_U_SAVING_UN_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table saving_unique
   add constraint FK_SAVING_U_SAVING_UN_CLIENT foreign key (Client_ID)
      references Client (Client_ID)
      on update restrict
      on delete restrict;

