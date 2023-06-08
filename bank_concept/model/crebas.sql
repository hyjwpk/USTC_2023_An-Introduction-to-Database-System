create table client 
(
   client_id            char(18)                       not null,
   real_name            char(30)                       null,
   client_phone         char(11)                       null,
   client_address       char(30)                       null,
   client_email         char(30)                       null,
   constraint PK_CLIENT primary key (client_id)
);

create table credit_account 
(
   account_id           integer                        not null,
   client_id            char(18)                       not null,
   bank_name            char(30)                       not null,
   password             char(20)                       null,
   remaining            integer                        null,
   open_date            date                           null,
   overdraft            integer                        null,
   constraint PK_CREDIT_ACCOUNT primary key clustered (account_id)
);

create table department 
(
   depart_no            integer                        not null,
   id                   integer                        null,
   bank_name            char(30)                       not null,
   depart_name          char(30)                       null,
   depart_type          char(30)                       null,
   constraint PK_DEPARTMENT primary key (depart_no)
);

create table loan 
(
   loan_id              integer                        not null,
   client_id            char(18)                       not null,
   bank_name            char(30)                       not null,
   loan_total           integer                        null,
   remain_loan          integer                        null,
   loan_date            date                           null,
   loan_rate            float                          null,
   constraint PK_LOAN primary key (loan_id)
);

create table member 
(
   id                   integer                        not null,
   depart_no            integer                        null,
   dep_depart_no        integer                        not null,
   bank_name            char(30)                       not null,
   name                 char(30)                       null,
   sex                  char(1)                        null,
   person_id            char(18)                       null,
   phone                char(11)                       null,
   address              char(30)                       null,
   salary               integer                        null,
   begin_date           date                           null,
   level                integer                        null,
   constraint PK_MEMBER primary key (id)
);

create table pay_status 
(
   pay_id               char(30)                       not null,
   loan_id              integer                        not null,
   pay_money            integer                        null,
   pay_date             date                           null,
   constraint PK_PAY_STATUS primary key (pay_id)
);

create table saving_account 
(
   account_id           integer                        not null,
   client_id            char(18)                       not null,
   bank_name            char(30)                       not null,
   password             char(20)                       null,
   remaining            integer                        null,
   open_date            date                           null,
   interest_rate        float                          null,
   constraint PK_SAVING_ACCOUNT primary key clustered (account_id)
);

create table sub_bank 
(
   bank_name            char(30)                       not null,
   bank_location        char(30)                       null,
   asset                integer                        null,
   constraint PK_SUB_BANK primary key (bank_name)
);

alter table credit_account
   add constraint FK_CREDIT_A_OPEN_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table credit_account
   add constraint FK_CREDIT_A_OWN_CLIENT foreign key (client_id)
      references client (client_id)
      on update restrict
      on delete restrict;

alter table department
   add constraint FK_DEPARTME_INCLUDE_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table department
   add constraint FK_DEPARTME_MANAGE_MEMBER foreign key (id)
      references member (id)
      on update restrict
      on delete restrict;

alter table loan
   add constraint FK_LOAN_LEND_BANK_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table loan
   add constraint FK_LOAN_LEND_CLIE_CLIENT foreign key (client_id)
      references client (client_id)
      on update restrict
      on delete restrict;

alter table member
   add constraint FK_MEMBER_EMPLOY_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table member
   add constraint FK_MEMBER_MANAGE2_DEPARTME foreign key (depart_no)
      references department (depart_no)
      on update restrict
      on delete restrict;

alter table member
   add constraint FK_MEMBER_WORK_IN_DEPARTME foreign key (dep_depart_no)
      references department (depart_no)
      on update restrict
      on delete restrict;

alter table pay_status
   add constraint FK_PAY_STAT_PAY_LOAN_LOAN foreign key (loan_id)
      references loan (loan_id)
      on update restrict
      on delete restrict;

alter table saving_account
   add constraint FK_SAVING_A_OPEN2_SUB_BANK foreign key (bank_name)
      references sub_bank (bank_name)
      on update restrict
      on delete restrict;

alter table saving_account
   add constraint FK_SAVING_A_OWN2_CLIENT foreign key (client_id)
      references client (client_id)
      on update restrict
      on delete restrict;

