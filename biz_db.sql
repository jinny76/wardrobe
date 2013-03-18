/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/3/13 14:55:32                           */
/*==============================================================*/


drop table if exists BT_ATTACHMENT_INFO;

drop table if exists BT_CHANNEL_2_USER_INFO;

drop table if exists BT_CHANNEL_INFO;

drop table if exists BT_COLLOCATION_TEMPLATE_INFO;

drop table if exists BT_USER_ATTENTION_INFO;

drop table if exists BT_USER_CLOSET_INFO;

drop table if exists BT_USER_COLLOCATION_2_CLOSET_INFO;

drop table if exists BT_USER_COLLOCATION_INFO;

drop table if exists BT_USER_COLLOCATION_REVIEW_INFO;

drop table if exists BT_USER_INFO;

drop table if exists BT_USER_MESSAGE_INFO;

/*==============================================================*/
/* Table: BT_ATTACHMENT_INFO                                    */
/*==============================================================*/
create table BT_ATTACHMENT_INFO
(
   ATTACHMENT_ID        integer not null,
   NAME                 varchar(50),
   TYPE                 varchar(20),
   LOCATION             varchar(2000),
   SIZE                 double,
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (ATTACHMENT_ID)
);

alter table BT_ATTACHMENT_INFO comment '附件信息';

/*==============================================================*/
/* Table: BT_CHANNEL_2_USER_INFO                                */
/*==============================================================*/
create table BT_CHANNEL_2_USER_INFO
(
   C_ID                 integer not null,
   USER_ID              integer not null,
   LOGIN_NAME           varchar(50),
   LOGIN_PWD            varchar(20),
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (C_ID, USER_ID)
);

alter table BT_CHANNEL_2_USER_INFO comment '渠道用户对应信息';

/*==============================================================*/
/* Table: BT_CHANNEL_INFO                                       */
/*==============================================================*/
create table BT_CHANNEL_INFO
(
   C_ID                 integer not null,
   C_NAME               varchar(50),
   C_TYPE               varchar(20),
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (C_ID)
);

alter table BT_CHANNEL_INFO comment '渠道信息';

/*==============================================================*/
/* Table: BT_COLLOCATION_TEMPLATE_INFO                          */
/*==============================================================*/
create table BT_COLLOCATION_TEMPLATE_INFO
(
   M_ID                 integer not null,
   ATTACHMENT_ID        integer,
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (M_ID)
);

alter table BT_COLLOCATION_TEMPLATE_INFO comment '搭配模板信息';

/*==============================================================*/
/* Table: BT_USER_ATTENTION_INFO                                */
/*==============================================================*/
create table BT_USER_ATTENTION_INFO
(
   A_ID                 integer not null,
   USER_ID              integer,
   AU_ID                integer,
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (A_ID)
);

alter table BT_USER_ATTENTION_INFO comment '用户关注信息';

/*==============================================================*/
/* Table: BT_USER_CLOSET_INFO                                   */
/*==============================================================*/
create table BT_USER_CLOSET_INFO
(
   CL_ID                integer not null,
   USER_ID              integer,
   ATTACHMENT_ID        integer,
   CLOSET_TYPE          integer,
   A_ID                 integer,
   CLIENT_NAME          varchar(500),
   CLIENT_CR_DT         date,
   COMMENT              varchar(2000),
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (CL_ID)
);

alter table BT_USER_CLOSET_INFO comment '用户衣橱信息';

/*==============================================================*/
/* Table: BT_USER_COLLOCATION_2_CLOSET_INFO                     */
/*==============================================================*/
create table BT_USER_COLLOCATION_2_CLOSET_INFO
(
   UCO_2_UC_ID          integer not null,
   CO_ID                integer,
   CL_ID                integer,
   POSITION_ID          integer,
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (UCO_2_UC_ID)
);

alter table BT_USER_COLLOCATION_2_CLOSET_INFO comment '附件信息';

/*==============================================================*/
/* Table: BT_USER_COLLOCATION_INFO                              */
/*==============================================================*/
create table BT_USER_COLLOCATION_INFO
(
   CO_ID                integer not null,
   USER_ID              integer,
   M_ID                 integer,
   ATTACHMENT_ID        integer,
   SUM_GOOD             integer,
   COMMENT              varchar(2000),
   CLIENT_NAME          varchar(500),
   CLIENT_CR_DT         date,
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (CO_ID)
);

alter table BT_USER_COLLOCATION_INFO comment '用户衣橱信息';

/*==============================================================*/
/* Table: BT_USER_COLLOCATION_REVIEW_INFO                       */
/*==============================================================*/
create table BT_USER_COLLOCATION_REVIEW_INFO
(
   COR_ID               integer not null,
   CO_ID                integer,
   USER_ID              integer,
   GOOD_TAG             integer,
   COMMENT              varchar(2000),
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (COR_ID)
);

alter table BT_USER_COLLOCATION_REVIEW_INFO comment '用户衣橱信息';

/*==============================================================*/
/* Table: BT_USER_INFO                                          */
/*==============================================================*/
create table BT_USER_INFO
(
   USER_ID              integer not null,
   LOGIN_NAME           varchar(50),
   LOGIN_PWD            varchar(20),
   PICK_NAME            varchar(50),
   GENDER               varchar(10),
   HEIGHT               double(6,3),
   WEIGHT               double(6,3),
   BIRTHDAY             date,
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (USER_ID)
);

alter table BT_USER_INFO comment '用户信息';

/*==============================================================*/
/* Table: BT_USER_MESSAGE_INFO                                  */
/*==============================================================*/
create table BT_USER_MESSAGE_INFO
(
   M_ID                 integer not null,
   USER_ID              integer not null,
   SENDER_ID            integer not null,
   CONTENT              varchar(2000),
   M_TYPE               varchar(10),
   CR_DT                date,
   UP_DT                date,
   DEL_DT               date,
   REDUNDANCY_1         varchar(2000),
   REDUNDANCY_2         varchar(2000),
   REDUNDANCY_3         varchar(2000),
   REDUNDANCY_4         varchar(2000),
   REDUNDANCY_5         varchar(2000),
   primary key (M_ID)
);

alter table BT_USER_MESSAGE_INFO comment '用户关注信息';

