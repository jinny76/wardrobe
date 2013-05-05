DROP TABLE artifact_item;
DROP TABLE collocation;
DROP TABLE collocation_comments;
DROP TABLE person;
DROP TABLE person_relations;
DROP TABLE person_messages;

CREATE TABLE artifact_item(id VARCHAR(32),pic_id VARCHAR(32), owner_id VARCHAR(32),latitude1 VARCHAR(100),latitude2 VARCHAR(100),description VARCHAR(500),TYPE VARCHAR(100),create_date DATE,PRIMARY KEY(id));
CREATE TABLE collocation(id VARCHAR(32),temmplate_id VARCHAR(32), owner_id VARCHAR(32),pic_id VARCHAR(100),description VARCHAR(500),create_date DATE,PRIMARY KEY(id));
CREATE TABLE collocation_comments(id VARCHAR(32),comments VARCHAR(500), collocation_id VARCHAR(32),owner_id VARCHAR(100),create_date DATE,PRIMARY KEY(id));
CREATE TABLE person(id VARCHAR(32),portrait_id VARCHAR(32), nick VARCHAR(100),signature VARCHAR(100),gender VARCHAR(20), mail VARCHAR(100),mobile VARCHAR(100), sina VARCHAR(100),tencent VARCHAR(100),renren VARCHAR(100),douban VARCHAR(100),create_date DATE,login_date DATE, PRIMARY KEY(id));

ALTER TABLE person ADD observers_counter DECIMAL(18,0);
ALTER TABLE person ADD fans_counter DECIMAL(18,0);
ALTER TABLE person ADD friends_counter DECIMAL(18,0);
ALTER TABLE collocation ADD comments_counter DECIMAL(18,0);

CREATE TABLE person_relations(id VARCHAR(32),person_master VARCHAR(32), person_link VARCHAR(100),create_date DATE,TYPE DECIMAL(1,0), PRIMARY KEY(id));
CREATE TABLE person_messages(id VARCHAR(32),send_from VARCHAR(32), send_to VARCHAR(32),msg VARCHAR(500),create_date DATE,TYPE DECIMAL(1,0), PRIMARY KEY(id));
ALTER TABLE person_messages ADD read_already DECIMAL(18);
ALTER TABLE person ADD pwd VARCHAR(32);

ALTER TABLE collocation ADD illegal DECIMAL(1);
ALTER TABLE collocation ADD shown DECIMAL(1);
ALTER TABLE collocation ADD report_msg VARCHAR(500);
ALTER TABLE collocation ADD report_by VARCHAR(32);
ALTER TABLE collocation ADD adore_counter VARCHAR(32);
ALTER TABLE collocation ADD description VARCHAR(500);
ALTER TABLE collocation ADD artifact_item_ids VARCHAR(500);
ALTER TABLE collocation ADD template_id VARCHAR(500);

ALTER TABLE person ADD offence_report VARCHAR(500);
ALTER TABLE person ADD offence_report_by VARCHAR(32);
ALTER TABLE person ADD offence_report_date DATE;
alter table person add birthday varchar2(20);
