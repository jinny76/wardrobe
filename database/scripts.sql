create table artifact_item(id varchar2(32),pic_id varchar2(32), owner_id varchar2(32),latitude1 varchar2(100),latitude2 varchar2(100),description varchar2(500),type varchar2(100),create_date date,primary key(id));
create table collocation(id varchar2(32),temmplate_id varchar2(32), owner_id varchar2(32),pic_id varchar2(100),description varchar2(500),create_date date,primary key(id));
create table collocation_comments(id varchar2(32),comments varchar2(500), collocation_id varchar2(32),owner_id varchar2(100),create_date date,primary key(id));
create table person(id varchar2(32),portrait_id varchar2(32), nick varchar2(100),signature varchar2(100),gender varchar2(20), mail varchar2(100),mobile varchar2(100), sina varchar2(100),tencent varchar2(100),renren varchar2(100),douban varchar2(100),create_date date,login_date date, primary key(id));

alter table person add observers_counter number(18,0);
alter table person add fans_counter number(18,0);
alter table person add friends_counter number(18,0);
alter table collocation add comments_counter number(18,0);

create table person_relations(id varchar2(32),person_master varchar2(32), person_link varchar2(100),create_date date,type number(1,0), primary key(id));
create table person_messages(id varchar2(32),send_from varchar2(32), send_to varchar2(32),msg varchar2(500),create_date date,type number(1,0), primary key(id));
alter table person_messages add read number(18,0);
alter table person add pwd varchar2(32);

alter table collocation add illegal number(1,0);
alter table collocation add show number(1,0);
alter table collocation add report_msg varchar2(500);
alter table collocation add report_by varchar2(32);
alter table collocation add adore_counter varchar2(32);
alter table collocation add description varchar2(500);
alter table collocation add artifact_item_ids varchar2(500);

alter table person add offence_report varchar2(500);
alter table person add offence_report_by varchar2(32);
alter table person add offence_report_date date;
