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

create table Client 
(
   Client_ID            integer                        not null,
   Real_name            char(30)                       null,
   Client_phone         integer                        null,
   Client_address       char(30)                       null,
   Client_email         char(30)                       null,
   constraint PK_CLIENT primary key (Client_ID)
);

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

create table Pay_status 
(
   Pay_ID               char(30)                       not null,
   Loan_ID              integer                        not null,
   Pay_money            integer                        null,
   Pay_date             date                           null,
   constraint PK_PAY_STATUS primary key (Pay_ID)
);

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

create table Serve 
(
   ID                   integer                        not null,
   Client_ID            integer                        not null,
   constraint PK_SERVE primary key clustered (ID, Client_ID)
);

create table credit_unique 
(
   bank_name            char(30)                       not null,
   Accout_ID            integer                        not null,
   Client_ID            integer                        not null,
   constraint PK_CREDIT_UNIQUE primary key clustered (bank_name, Accout_ID, Client_ID)
);

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

create table saving_unique 
(
   Accout_ID            integer                        not null,
   bank_name            char(30)                       not null,
   Client_ID            integer                        not null,
   constraint PK_SAVING_UNIQUE primary key clustered (Accout_ID, bank_name, Client_ID)
);

create table sub_bank 
(
   bank_name            char(30)                       not null,
   bank_location        char(30)                       null,
   asset                integer                        null,
   constraint PK_SUB_BANK primary key (bank_name)
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

