create table artifact_item(id varchar(32),pic_id varchar(32), owner_id varchar(32),latitude1 varchar(100),latitude2 varchar(100),description varchar(500),type varchar(100),create_date date,primary key(id));
create table collocation(id varchar(32),temmplate_id varchar(32), owner_id varchar(32),pic_id varchar(100),description varchar(500),create_date date,primary key(id));
create table collocation_comments(id varchar(32),comments varchar(500), collocation_id varchar(32),owner_id varchar(100),create_date date,primary key(id));
create table person(id varchar(32),portrait_id varchar(32), nick varchar(100),signature varchar(100),gender varchar(20), mail varchar(100),mobile varchar(100), sina varchar(100),tencent varchar(100),renren varchar(100),douban varchar(100),create_date date,login_date date, primary key(id));

alter table person add observers_counter numeric(18,0);
alter table person add fans_counter numeric(18,0);
alter table person add friends_counter numeric(18,0);
alter table collocation add comments_counter numeric(18,0);

create table person_relations(id varchar(32),person_master varchar(32), person_link varchar(100),create_date date,type numeric(1,0), primary key(id));
create table person_messages(id varchar(32),send_from varchar(32), send_to varchar(32),msg varchar(500),create_date date,type numeric(1,0), primary key(id));
alter table person_messages add read numeric(18,0);
alter table person add pwd varchar(32);

alter table collocation add illegal numeric(1,0);
alter table collocation add show numeric(1,0);
alter table collocation add report_msg varchar(500);
alter table collocation add report_by varchar(32);
alter table collocation add adore_counter varchar(32);
alter table collocation add description varchar(500);
alter table collocation add artifact_item_ids varchar(500);
alter table collocation add template_id varchar(500);

alter table person add offence_report varchar(500);
alter table person add offence_report_by varchar(32);
alter table person add offence_report_date date;
