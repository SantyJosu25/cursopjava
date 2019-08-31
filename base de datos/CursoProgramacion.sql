/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     31/8/2019 11:06:33                           */
/*==============================================================*/


drop index RELATIONSHIP_1_FK;

drop index GENERATOR_CODE_PK;

drop table GENERATOR_CODE;

drop index RELATIONSHIP_2_FK;

drop index MESSAGE_PK;

drop table MESSAGE;

drop index USER_PK;

drop table "USER";

/*==============================================================*/
/* Table: GENERATOR_CODE                                        */
/*==============================================================*/
create table GENERATOR_CODE (
   ID_CODE              INT4                 not null,
   ID_USER              INT4                 not null,
   COD_CODE             VARCHAR(5)           null,
   COD_DATA_START       DATE                 null,
   COD_DATA_END         DATE                 null,
   constraint PK_GENERATOR_CODE primary key (ID_CODE)
);

/*==============================================================*/
/* Index: GENERATOR_CODE_PK                                     */
/*==============================================================*/
create unique index GENERATOR_CODE_PK on GENERATOR_CODE (
ID_CODE
);

/*==============================================================*/
/* Index: RELATIONSHIP_1_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_1_FK on GENERATOR_CODE (
ID_USER
);

/*==============================================================*/
/* Table: MESSAGE                                               */
/*==============================================================*/
create table MESSAGE (
   IDE_MESSAGE          INT4                 not null,
   ID_CODE              INT4                 not null,
   VALUE_MESSAGE        VARCHAR(100)         null,
   constraint PK_MESSAGE primary key (IDE_MESSAGE)
);

/*==============================================================*/
/* Index: MESSAGE_PK                                            */
/*==============================================================*/
create unique index MESSAGE_PK on MESSAGE (
IDE_MESSAGE
);

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_2_FK on MESSAGE (
ID_CODE
);

/*==============================================================*/
/* Table: "USER"                                                */
/*==============================================================*/
create table "USER" (
   ID_USER              INT4                 not null,
   US_USER              VARCHAR(50)          null,
   US_USERNAME          VARCHAR(50)          null,
   US_PASS              VARCHAR(50)          null,
   US_EMAIL             VARCHAR(60)          null,
   constraint PK_USER primary key (ID_USER)
);

/*==============================================================*/
/* Index: USER_PK                                               */
/*==============================================================*/
create unique index USER_PK on "USER" (
ID_USER
);

alter table GENERATOR_CODE
   add constraint FK_GENERATO_RELATIONS_USER foreign key (ID_USER)
      references "USER" (ID_USER)
      on delete restrict on update restrict;

alter table MESSAGE
   add constraint FK_MESSAGE_RELATIONS_GENERATO foreign key (ID_CODE)
      references GENERATOR_CODE (ID_CODE)
      on delete restrict on update restrict;

